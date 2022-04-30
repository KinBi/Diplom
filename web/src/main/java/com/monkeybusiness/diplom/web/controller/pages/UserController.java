package com.monkeybusiness.diplom.web.controller.pages;

import com.monkeybusiness.core.model.practice.Practice;
import com.monkeybusiness.core.model.service.PracticeService;
import com.monkeybusiness.core.model.service.UserService;
import com.monkeybusiness.core.model.user.Roles;
import com.monkeybusiness.core.model.user.User;
import com.monkeybusiness.diplom.web.controller.dto.MessageDto;
import com.monkeybusiness.diplom.web.controller.validation.LoginWrapper;
import com.monkeybusiness.diplom.web.controller.validation.UserFullWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
  public static final String DELIMITER = "\n";
  public static final String ADD_SUCCESS_MESSAGE = "Added successfully";
  public static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully";
  public static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully";

  @Autowired
  private UserService userService;

  @Autowired
  private PracticeService practiceService;

  @GetMapping("/users")
  @ResponseBody
  public List<User> getUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/practices")
  @ResponseBody
  public List<Practice> getPractices() {
    return practiceService.getAllPractices();
  }

//  @DeleteMapping
//  @ResponseBody
//  public MessageDto deleteUser(@RequestBody @Valid LoginWrapper idWrapper, BindingResult bindingResult) {
//    boolean successful = true;
//    if (bindingResult.hasErrors()) {
//      successful = false;
//    } else {
//      userService.delete(idWrapper.getId());
//    }
//    return createUserAdminDto(successful, bindingResult, DELETE_SUCCESS_MESSAGE);
//  }

  @PutMapping
  @ResponseBody
  public MessageDto updateUser(@RequestBody @Valid UserFullWrapper userFullWrapper, BindingResult bindingResult) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User user = new User();
      user.setName(userFullWrapper.getName());
      user.setSurname(userFullWrapper.getSurname());
      user.setMiddleName(userFullWrapper.getMiddleName());
      user.setLogin(userFullWrapper.getLogin());
      user.setPassword(userFullWrapper.getPassword());
      user.setRole(Roles.STUDENT.name());
      userService.update(user);
    }
    return createUserAdminDto(successful, bindingResult, UPDATE_SUCCESS_MESSAGE);
  }

  @PostMapping
  @ResponseBody
  public MessageDto addUser(@RequestBody @Valid UserFullWrapper userFullWrapper, BindingResult bindingResult) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User user = new User();
      user.setName(userFullWrapper.getName());
      user.setSurname(userFullWrapper.getSurname());
      user.setMiddleName(userFullWrapper.getMiddleName());
      user.setLogin(userFullWrapper.getLogin());
      user.setPassword(userFullWrapper.getPassword());
      user.setRole(Roles.STUDENT.name());
      userService.save(user);
    }
    return createUserAdminDto(successful, bindingResult, ADD_SUCCESS_MESSAGE);
  }

  private MessageDto createUserAdminDto(boolean successful, BindingResult bindingResult, String successMessage) {
    MessageDto userAdminDto = new MessageDto();
    userAdminDto.setSuccessful(successful);
    String message;
    if (!successful) {
      message = bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(DELIMITER));
    } else {
      message = successMessage;
    }
    userAdminDto.setMessage(message);
    return userAdminDto;
  }
}
