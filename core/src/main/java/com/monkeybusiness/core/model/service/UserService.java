package com.monkeybusiness.core.model.service;

import com.monkeybusiness.core.model.user.User;

import java.util.List;

public interface UserService {
  User getUser(Long id);

  User getUserByUsername(String username);

  List<User> getAllUsers();

  void save(User user);

  void update(User user);

  void delete(Long id);
}
