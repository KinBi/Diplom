package com.monkeybusiness.diplom.web.controller.validation;

import com.monkeybusiness.core.model.practice.PracticeStatus;
import org.hibernate.validator.constraints.NotEmpty;

public class PracticeWrapper {
  private Long id;

  @NotEmpty
  private String practiceDateStart;

  @NotEmpty
  private String practiceDateEnd;

  @NotEmpty
  private String location;

  private PracticeStatus status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPracticeDateStart() {
    return practiceDateStart;
  }

  public void setPracticeDateStart(String practiceDateStart) {
    this.practiceDateStart = practiceDateStart;
  }

  public String getPracticeDateEnd() {
    return practiceDateEnd;
  }

  public void setPracticeDateEnd(String practiceDateEnd) {
    this.practiceDateEnd = practiceDateEnd;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public PracticeStatus getStatus() {
    return status;
  }

  public void setStatus(PracticeStatus status) {
    this.status = status;
  }
}
