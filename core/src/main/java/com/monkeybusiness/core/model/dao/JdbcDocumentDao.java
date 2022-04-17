package com.monkeybusiness.core.model.dao;

import com.monkeybusiness.core.model.document.Document;
import com.monkeybusiness.core.model.document.DocumentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcDocumentDao implements DocumentDao {
  public static final String SELECT_DOCUMENT_BY_ID = "SELECT * FROM documents WHERE id = ?";
  public static final String SELECT_ALL_DOCUMENTS = "SELECT * FROM documents";
  public static final String UPDATE_DOCUMENT = "UPDATE documents SET path = ?, userId = ?, statusId = ? WHERE id = ?";
  public static final String SELECT_STATUS_BY_DOCUMENT_ID = "SELECT documentStatuses.status FROM documentStatuses " +
          "JOIN practices ON practices.statusId = practiceStatuses.id WHERE practices.id = ?";
  public static final String DELETE_DOCUMENT = "DELETE FROM documents WHERE id = ?";
  public static final String DOCUMENTS_TABLE = "documents";
  public static final String ID_COLUMN = "id";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private SimpleJdbcInsert simpleJdbcInsert;

  @PostConstruct
  private void init() {
    simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(DOCUMENTS_TABLE)
            .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public Optional<Document> find(Long id) {
    Optional<Document> optionalDocument;
    List<Document> documentList = jdbcTemplate.query(SELECT_DOCUMENT_BY_ID,
            new BeanPropertyRowMapper<>(Document.class), id);
    optionalDocument = documentList.isEmpty() ? Optional.empty() : Optional.ofNullable(documentList.get(0));
    optionalDocument.ifPresent(this::setStatus);
    return optionalDocument;
  }

  private void setStatus(Document document) {
    document.setDocumentStatus(jdbcTemplate.query(SELECT_STATUS_BY_DOCUMENT_ID,
            new BeanPropertyRowMapper<>(DocumentStatus.class), document.getId()).get(0));
  }

  @Override
  public List<Document> findAll() {
    List<Document> documents;
    documents = jdbcTemplate.query(SELECT_ALL_DOCUMENTS, new BeanPropertyRowMapper<>(Document.class));
    documents.forEach(this::setStatus);
    return documents;
  }

  @Override
  public void save(Document document) {
    addPractice(document);
  }

  private void addPractice(Document document) {
    SqlParameterSource source = new BeanPropertySqlParameterSource(document);
    Long id = (Long) simpleJdbcInsert.executeAndReturnKey(source);
    document.setId(id);
  }

  @Override
  public void update(Document document) {
    jdbcTemplate.update(UPDATE_DOCUMENT, document.getPath(),
            document.getUserId(), document.getDocumentStatus().getId(), document.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(DELETE_DOCUMENT, id);
  }
}
