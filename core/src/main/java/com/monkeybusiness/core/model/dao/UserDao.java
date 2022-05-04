package com.monkeybusiness.core.model.dao;

import com.monkeybusiness.core.model.user.Group;
import com.monkeybusiness.core.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
  Optional<User> find(Long id);

  Optional<User> findByUsername(String username);

  List<User> findByGroup(Group group);

  List<User> findAll();

  void save(User user);

  void update(User user);

  void updateRole(User user);

  void delete(Long id);
}
