package com.monkeybusiness.core.model.dao;


import com.monkeybusiness.core.model.user.Group;

import java.util.List;
import java.util.Optional;

public interface GroupDao {
  Optional<Group> find(Long id);

  Optional<Group> findByCode(String code);

  List<Group> findAll();

  void save(Group group);

  void update(Group group);

  void delete(Long id);
}
