package com.monkeybusiness.diplom.web.controller.validation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class IdWrapper {
  @NotNull(message = "Id cannot be empty")
  @Min(1)
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
