package com.monkeybusiness.core.model.service;

import com.monkeybusiness.core.model.dao.UserDao;
import com.monkeybusiness.core.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao jdbcUserDao;

  @Override
  public User getUser(Long id) {
    Optional<User> optionalUser = jdbcUserDao.find(id);
    return optionalUser.orElseThrow(RuntimeException::new);
  }

  @Override
  public User getUserByUsername(String username) {
    Optional<User> optionalUser = jdbcUserDao.findByUsername(username);
    return optionalUser.orElseThrow(RuntimeException::new);
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = jdbcUserDao.findAll();
    return users;
  }

  @Override
  public void save(User user) {
    jdbcUserDao.save(user);
  }

  @Override
  public void update(User user) {
    jdbcUserDao.update(user);
  }

  @Override
  public void delete(Long id) {
    jdbcUserDao.delete(id);
  }
}
