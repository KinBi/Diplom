package com.monkeybusiness.core.model.service;

import com.monkeybusiness.core.model.document.Document;
import com.monkeybusiness.core.model.user.User;

import java.util.List;

public interface DocumentService {
  Document getDocument(Long id);

  List<Document> getAllDocuments();

  void save(Document document);

  void update(Document document);

  void delete(Long id);
}
