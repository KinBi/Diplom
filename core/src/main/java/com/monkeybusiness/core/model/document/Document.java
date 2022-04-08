package com.monkeybusiness.core.model.document;

import java.util.Objects;

public class Document {
  private Long id;
  private String path;
  private Long userId;
  private DocumentStatus documentStatus;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public DocumentStatus getDocumentStatus() {
    return documentStatus;
  }

  public void setDocumentStatus(DocumentStatus documentStatus) {
    this.documentStatus = documentStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Document document = (Document) o;
    return Objects.equals(id, document.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
