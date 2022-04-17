package com.monkeybusiness.core.model.dao;

import com.monkeybusiness.core.model.user.Group;
import com.monkeybusiness.core.model.user.Role;
import com.monkeybusiness.core.model.user.Speciality;
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
  public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
  public static final String SELECT_ALL_USERS = "SELECT * FROM users";
  public static final String SELECT_USERS_ID_BY_USERNAME = "SELECT users.id FROM users WHERE username = ?";
  public static final String UPDATE_USER = "UPDATE users SET username = ?, password = ?, roleId = ?, practiceId = ?, groupId = ? " +
          "WHERE id = ?";
  public static final String SELECT_ROLE_BY_USER_ID = "SELECT roles.role FROM roles " +
          "JOIN users ON users.roleId = roles.id WHERE users.id = ?";
  public static final String SELECT_GROUP_ID_BY_USER_ID = "SELECT groups.id FROM groups " +
          "JOIN users ON users.groupId = groups.id WHERE users.id = ?";
  public static final String SELECT_SPECIALITY_BY_GROUP_ID = "SELECT * FROM specialities " +
          "JOIN groups ON groups.specialityId = specialities.id WHERE groups.id = ?";
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
    Optional<User> optionalUser;
    List<User> userList = jdbcTemplate.query(SELECT_USER_BY_ID, new BeanPropertyRowMapper<>(User.class), id);
    optionalUser = userList.isEmpty() ? Optional.empty() : Optional.ofNullable(userList.get(0));
    optionalUser.ifPresent(this::setRole);
    optionalUser.ifPresent(this::setGroup);
    return optionalUser;
  }

  @Override
  public Optional<User> findByUsername(String username) {
    Optional<User> optionalUser;
    List<User> userList = jdbcTemplate.query(SELECT_USER_BY_USERNAME, new BeanPropertyRowMapper<>(User.class), username);
    optionalUser = userList.isEmpty() ? Optional.empty() : Optional.ofNullable(userList.get(0));
    optionalUser.ifPresent(this::setRole);
    optionalUser.ifPresent(this::setGroup);
    return optionalUser;
  }

  private void setRole(User user) {
    user.setRole(jdbcTemplate.query(SELECT_ROLE_BY_USER_ID,
            new BeanPropertyRowMapper<>(Role.class), user.getId()).get(0));
  }

  private void setGroup(User user) {
    Group group = new Group();
    List<Long> groupIds = jdbcTemplate.queryForList(SELECT_GROUP_ID_BY_USER_ID,
            Long.class, user.getId());
    group.setId(groupIds.get(0));
    user.setGroup(group);
    setSpeciality(group);
  }

  private void setSpeciality(Group group) {
    group.setSpeciality(jdbcTemplate.query(SELECT_SPECIALITY_BY_GROUP_ID,
            new BeanPropertyRowMapper<>(Speciality.class), group.getId()).get(0));
  }

  @Override
  public List<User> findAll() {
    List<User> users;
    users = jdbcTemplate.query(SELECT_ALL_USERS, new BeanPropertyRowMapper<>(User.class));
    users.forEach(this::setRole);
    users.forEach(this::setGroup);
    return users;
  }

  @Override
  public void save(User user) {
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
    user.setId(getUserId(user).orElseThrow(RuntimeException::new)); // fixme
    jdbcTemplate.update(UPDATE_USER, user.getUsername(), user.getPassword(), user.getRole().getId(),
            user.getPracticeId(), user.getGroup().getId(), user.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(DELETE_USER, id);
  }
}
