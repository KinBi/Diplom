package com.monkeybusiness.core.model.user;

import java.util.Objects;

public class Group {
  private Long id;
  private Speciality speciality;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Speciality getSpeciality() {
    return speciality;
  }

  public void setSpeciality(Speciality speciality) {
    this.speciality = speciality;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Group group = (Group) o;
    return Objects.equals(id, group.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
