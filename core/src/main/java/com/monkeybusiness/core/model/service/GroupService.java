package com.monkeybusiness.core.model.service;

import com.monkeybusiness.core.model.user.Group;

import java.util.List;

public interface GroupService {
  Group getGroup(Long id);

  Group getGroupByCode(String code);

  List<Group> getAllGroups();

  void save(Group group);

  void update(Group group);

  void delete(Long id);
}
