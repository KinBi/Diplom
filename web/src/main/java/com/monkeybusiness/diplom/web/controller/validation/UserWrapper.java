package com.monkeybusiness.diplom.web.controller.validation;

import org.hibernate.validator.constraints.NotEmpty;

public class UserWrapper {
  @NotEmpty(message = "Invalid username")
  private String login;

  @NotEmpty(message = "Invalid password")
  private String password;

  public UserWrapper() {
  }

  public UserWrapper(String login, String password) {
    this.login = login;
    this.password = password;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
