package com.monkeybusiness.diplom.web.controller.validation;

import com.monkeybusiness.core.model.user.Group;
import com.monkeybusiness.core.model.user.Role;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserFullWrapper {
  private Long id;

  @NotEmpty(message = "Cannot be empty")
  private String username;

  @NotEmpty(message = "Cannot be empty")
  private String password;

  @NotEmpty(message = "Cannot be empty")
  private Role role;

  @NotNull
  @Min(value = 1, message = "Cannot be empty")
  private Long practiceId;

  private Group group;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Long getPracticeId() {
    return practiceId;
  }

  public void setPracticeId(Long practiceId) {
    this.practiceId = practiceId;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }
}
