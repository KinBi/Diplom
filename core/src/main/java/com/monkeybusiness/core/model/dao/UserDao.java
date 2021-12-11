package com.monkeybusiness.core.model.dao;

import com.monkeybusiness.core.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
  Optional<User> find(Long id);

  List<User> findAll();

  void save(User user);

  void update(User user);

  void delete(Long id);
}
