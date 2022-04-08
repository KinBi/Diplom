package com.monkeybusiness.core.model.dao;

import com.monkeybusiness.core.model.document.Document;
import com.monkeybusiness.core.model.practice.Practice;

import java.util.List;
import java.util.Optional;

public interface DocumentDao {
  Optional<Document> find(Long id);

  List<Document> findAll();

  void save(Document document);

  void update(Document document);

  void delete(Long id);
}
