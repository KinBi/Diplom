package com.monkeybusiness.diplom.web.controller.dto;

import java.util.List;

public class PracticesDto extends AbstractDto {
  private List<PracticeDto> practiceDtos;

  public List<PracticeDto> getPracticeDtos() {
    return practiceDtos;
  }

  public void setPracticeDtos(List<PracticeDto> practiceDtos) {
    this.practiceDtos = practiceDtos;
  }
}
