package com.monkeybusiness.core.model.dao;

import com.monkeybusiness.core.model.user.Role;
import com.monkeybusiness.core.model.user.User;
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
public class JdbcUserDao implements UserDao {
  public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
  public static final String SELECT_ALL_USERS = "SELECT * FROM users";
  public static final String SELECT_USERS_ID_BY_USERNAME = "SELECT users.id FROM users WHERE username = ?";
  public static final String UPDATE_USER = "UPDATE users SET username = ?, password = ?, roleId = ?";
  public static final String SELECT_ROLE_BY_USER_ID = "SELECT roles.role FROM roles " +
          "JOIN users ON users.roleId = roles.id WHERE users.id = ?";
  public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
  public static final String USERS_TABLE = "users";
  public static final String ID_COLUMN = "id";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private SimpleJdbcInsert simpleJdbcInsert;

  @PostConstruct
  private void init() {
    simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(USERS_TABLE)
            .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public Optional<User> find(Long id) {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    Optional<User> optionalUser;
    List<User> userList = jdbcTemplate.query(SELECT_USER_BY_ID, new BeanPropertyRowMapper<>(User.class), id);
    optionalUser = userList.isEmpty() ? Optional.empty() : Optional.ofNullable(userList.get(0));
    optionalUser.ifPresent(this::setRole);
    return optionalUser;
  }

  private void setRole(User user) {
    user.setRole(jdbcTemplate.query(SELECT_ROLE_BY_USER_ID,
            new BeanPropertyRowMapper<>(Role.class), user.getId()).get(0));
  }

  @Override
  public List<User> findAll() {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    List<User> users;
    users = jdbcTemplate.query(SELECT_ALL_USERS, new BeanPropertyRowMapper<>(User.class));
    users.forEach(this::setRole);
    return users;
  }

  @Override
  public void save(User user) {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    Optional<Long> optionalId = getUserId(user);
    if (optionalId.isPresent()) {
      update(user);
    } else {
      addUser(user);
    }
  }

  private void addUser(User user) {
    SqlParameterSource source = new BeanPropertySqlParameterSource(user);
    Long id = (Long) simpleJdbcInsert.executeAndReturnKey(source);
    user.setId(id);
  }

  private Optional<Long> getUserId(User user) {
    List<Long> idList = jdbcTemplate.queryForList(SELECT_USERS_ID_BY_USERNAME, Long.class, user.getUsername());
    return idList.isEmpty() ? Optional.empty() : Optional.ofNullable(idList.get(0));
  }

  @Override
  public void update(User user) {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    user.setId(getUserId(user).orElseThrow(RuntimeException::new)); // fixme
    jdbcTemplate.update(UPDATE_USER);
  }

  @Override
  public void delete(Long id) {
    // FIXME: 12/12/2021 GET INFO ABOUT LOCKS
    jdbcTemplate.update(DELETE_USER, id);
  }
}
