package com.monkeybusiness.core.model.service;

import com.monkeybusiness.core.model.practice.Practice;

import java.util.List;

public interface PracticeService {
  Practice getPractice(Long id);

  List<Practice> getAllPractices();

  void save(Practice practice);

  void update(Practice practice);

  void delete(Long id);
}
