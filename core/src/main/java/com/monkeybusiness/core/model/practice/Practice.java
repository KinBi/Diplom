package com.monkeybusiness.core.model.practice;

import java.time.LocalDate;
import java.util.Objects;

public class Practice {
  private Long id;
  private LocalDate date;
  private String location;
  private PracticeStatus status;
  private Integer mark;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
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

  public Integer getMark() {
    return mark;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Practice practice = (Practice) o;
    return Objects.equals(id, practice.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
