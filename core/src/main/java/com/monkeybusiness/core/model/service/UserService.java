package com.monkeybusiness.core.model.service;

import com.monkeybusiness.core.model.user.Group;
import com.monkeybusiness.core.model.user.User;

import java.util.List;

public interface UserService {
  User getUser(Long id);

  User getUserByLogin(String username);

  List<User> getUsersWithRoles(List<String> roles);

  List<User> getUsersByGroup(Group group);

  List<User> getAllUsers();

  void save(User user);

  void update(User user);

  void delete(Long id);

  void deleteByLogin(String login);
}
