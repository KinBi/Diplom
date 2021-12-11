package com.monkeybusiness.core.model.dao;

import com.monkeybusiness.core.model.practice.Practice;
import com.monkeybusiness.core.model.practice.PracticeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPracticeDao implements PracticeDao {
  public static final String SELECT_PRACTICE_BY_ID = "SELECT * FROM practices WHERE id = ?";
  public static final String SELECT_ALL_PRACTICES = "SELECT * FROM practices";
  public static final String UPDATE_PRACTICE = "UPDATE practices SET date = ?, location = ?, statusId = ?, mark = ?";
  public static final String SELECT_STATUS_BY_PRACTICE_ID = "SELECT practiceStatuses.status FROM practiceStatuses " +
          "JOIN practices ON practices.statusId = practiceStatuses.id WHERE practices.id = ?";
  public static final String PRACTICES_TABLE = "practices";
  public static final String ID_COLUMN = "id";
  public static final String DELETE_PRACTICE = "DELETE FROM practices WHERE id = ?";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private SimpleJdbcInsert simpleJdbcInsert;

  @PostConstruct
  private void init() {
    simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(PRACTICES_TABLE)
            .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public Optional<Practice> find(Long id) {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    Optional<Practice> optionalPractice;
    List<Practice> practiceList = jdbcTemplate.query(SELECT_PRACTICE_BY_ID,
            new BeanPropertyRowMapper<>(Practice.class), id);
    optionalPractice = practiceList.isEmpty() ? Optional.empty() : Optional.ofNullable(practiceList.get(0));
    optionalPractice.ifPresent(this::setStatus);
    return optionalPractice;
  }

  private void setStatus(Practice practice) {
    practice.setStatus(jdbcTemplate.query(SELECT_STATUS_BY_PRACTICE_ID,
            new BeanPropertyRowMapper<>(PracticeStatus.class), practice.getId()).get(0));
  }

  @Override
  public List<Practice> findAll() {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    List<Practice> practices;
    practices = jdbcTemplate.query(SELECT_ALL_PRACTICES, new BeanPropertyRowMapper<>(Practice.class));
    practices.forEach(this::setStatus);
    return practices;
  }

  @Override
  public void save(Practice practice) {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    addPractice(practice);
  }

  private void addPractice(Practice practice) {
    SqlParameterSource source = new BeanPropertySqlParameterSource(practice);
    Long id = (Long) simpleJdbcInsert.executeAndReturnKey(source);
    practice.setId(id);
  }

  @Override
  public void update(Practice practice) {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    jdbcTemplate.update(UPDATE_PRACTICE);
  }

  @Override
  public void delete(Long id) {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    jdbcTemplate.update(DELETE_PRACTICE, id);
  }
}
