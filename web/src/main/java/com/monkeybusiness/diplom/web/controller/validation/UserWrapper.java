package com.monkeybusiness.diplom.web.controller.validation;

import org.hibernate.validator.constraints.NotEmpty;

public class UserWrapper {
  @NotEmpty(message = "Invalid username")
  private String username;

  @NotEmpty(message = "Invalid password")
  private String password;

  public UserWrapper() {
  }

  public UserWrapper(String username, String password) {
    this.username = username;
    this.password = password;
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
}
