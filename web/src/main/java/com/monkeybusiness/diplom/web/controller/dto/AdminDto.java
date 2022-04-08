package com.monkeybusiness.diplom.web.controller.dto;

public class AdminDto {
  private String message;
  private boolean isSuccessful;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccessful() {
    return isSuccessful;
  }

  public void setSuccessful(boolean successful) {
    isSuccessful = successful;
  }
}
