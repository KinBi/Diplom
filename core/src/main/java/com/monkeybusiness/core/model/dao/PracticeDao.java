package com.monkeybusiness.core.model.dao;

import com.monkeybusiness.core.model.practice.Practice;

import java.util.List;
import java.util.Optional;

public interface PracticeDao {
  Optional<Practice> find(Long id);

  List<Practice> findAll();

  void save(Practice practice);

  void update(Practice practice);

  void delete(Long id);
}
