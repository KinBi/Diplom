package com.monkeybusiness.diplom.web.controller.pages;

import com.monkeybusiness.core.model.practice.Practice;
import com.monkeybusiness.core.model.service.PracticeService;
import com.monkeybusiness.core.model.service.UserService;
import com.monkeybusiness.core.model.user.User;
import com.monkeybusiness.diplom.web.controller.dto.HeadDto;
import com.monkeybusiness.diplom.web.controller.validation.IdWrapper;
import com.monkeybusiness.diplom.web.controller.validation.PracticeWrapper;
import com.monkeybusiness.diplom.web.controller.validation.UserFullWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/head")
public class HeadPageController {
  public static final String DELIMITER = "\n";
  public static final String ADD_SUCCESS_MESSAGE = "Added successfully";
  public static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully";
  public static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully";

  @Autowired
  private PracticeService practiceService;

  @Autowired
  private UserService userService;

  @GetMapping("/practices")
  @ResponseBody
  public List<Practice> getPractices() {
    return practiceService.getAllPractices();
  }

//  @DeleteMapping
//  @ResponseBody
//  public HeadDto deletePractice(@RequestBody @Valid IdWrapper idWrapper, BindingResult bindingResult) {
//    boolean successful = true;
//    if (bindingResult.hasErrors()) {
//      successful = false;
//    } else {
//      practiceService.delete(idWrapper.getId());
//    }
//    return createHeadDto(successful, bindingResult, DELETE_SUCCESS_MESSAGE);
//  }

//  @PutMapping
//  @ResponseBody
//  public HeadDto updatePractice(@RequestBody @Valid PracticeWrapper practiceWrapper, BindingResult bindingResult) {
//    boolean successful = true;
//    if (bindingResult.hasErrors()) {
//      successful = false;
//    } else {
//      Practice practice = new Practice();
//      practice.setId(practiceWrapper.getId());
//      practice.setPracticeDateStart(practiceWrapper.getPracticeDateStart());
//      practice.setPracticeDateEnd(practiceWrapper.getPracticeDateEnd());
//      practice.setLocation(practiceWrapper.getLocation());
//      practice.setStatus(practiceWrapper.getStatus());
//      practiceService.update(practice);
//    }
//    return createHeadDto(successful, bindingResult, UPDATE_SUCCESS_MESSAGE);
//  }

//  @PostMapping
//  @ResponseBody
//  public HeadDto addPractice(@RequestBody @Valid PracticeWrapper practiceWrapper, BindingResult bindingResult) {
//    boolean successful = true;
//    if (bindingResult.hasErrors()) {
//      successful = false;
//    } else {
//      Practice practice = new Practice();
//      practice.setId(practiceWrapper.getId());
//      practice.setPracticeDateStart(practiceWrapper.getPracticeDateStart());
//      practice.setPracticeDateEnd(practiceWrapper.getPracticeDateEnd());
//      practice.setLocation(practiceWrapper.getLocation());
//      practice.setStatus(practiceWrapper.getStatus());
//      practiceService.update(practice);
//    }
//    return createHeadDto(successful, bindingResult, ADD_SUCCESS_MESSAGE);
//  }

//  @PutMapping
//  @ResponseBody
//  public HeadDto updateUser(@RequestBody @Valid UserFullWrapper userAdminWrapper, BindingResult bindingResult) {
//    boolean successful = true;
//    if (bindingResult.hasErrors()) {
//      successful = false;
//    } else {
//      User user = new User();
//      user.setId(userAdminWrapper.getId());
//      user.setUsername(userAdminWrapper.getUsername());
//      user.setPassword(userAdminWrapper.getPassword());
//      user.setRole(userAdminWrapper.getRole());
//      user.setPracticeId(userAdminWrapper.getPracticeId());
//      userService.update(user);
//    }
//    return createHeadDto(successful, bindingResult, UPDATE_SUCCESS_MESSAGE);
//  }

  private HeadDto createHeadDto(boolean successful, BindingResult bindingResult, String successMessage) {
    HeadDto headDto = new HeadDto();
    headDto.setSuccessful(successful);
    String message;
    if (!successful) {
      message = bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(DELIMITER));
    } else {
      message = successMessage;
    }
    headDto.setMessage(message);
    return headDto;
  }
}
