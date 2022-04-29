package com.monkeybusiness.diplom.web.controller.dto;

public class MessageDto {
  private String message;
  private boolean successful;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccessful() {
    return successful;
  }

  public void setSuccessful(boolean successful) {
    this.successful = successful;
  }
}
