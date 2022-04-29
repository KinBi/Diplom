package com.monkeybusiness.diplom.web.controller.validation;

import com.monkeybusiness.core.model.user.Group;
import org.hibernate.validator.constraints.NotEmpty;

public class UserFullWrapper {
  @NotEmpty(message = "Cannot be empty")
  private String name;

  @NotEmpty(message = "Cannot be empty")
  private String surname;

  @NotEmpty(message = "Cannot be empty")
  private String middleName;

  private String login;

  @NotEmpty(message = "Cannot be empty")
  private String password;

  private String groupCode;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getGroupCode() {
    return groupCode;
  }

  public void setGroupCode(String groupCode) {
    this.groupCode = groupCode;
  }
}
