package com.monkeybusiness.diplom.web.controller.pages;

import com.monkeybusiness.core.model.practice.Practice;
import com.monkeybusiness.core.model.service.GroupService;
import com.monkeybusiness.core.model.service.PracticeService;
import com.monkeybusiness.core.model.service.UserService;
import com.monkeybusiness.core.model.user.Group;
import com.monkeybusiness.core.model.user.Roles;
import com.monkeybusiness.core.model.user.User;
import com.monkeybusiness.diplom.web.controller.dto.AbstractDto;
import com.monkeybusiness.diplom.web.controller.dto.MessageDto;
import com.monkeybusiness.diplom.web.controller.dto.UserDto;
import com.monkeybusiness.diplom.web.controller.dto.UsersDto;
import com.monkeybusiness.diplom.web.controller.validation.GroupWrapper;
import com.monkeybusiness.diplom.web.controller.validation.LoginWrapper;
import com.monkeybusiness.diplom.web.controller.validation.UserFullWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping("/admin")
public class AdminController {
  public static final String DELIMITER = "\n";
  public static final String ADD_SUCCESS_MESSAGE = "Added successfully";
  public static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully";
  public static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully";

  @Autowired
  private UserService userService;

  @Autowired
  private GroupService groupService;

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

  @GetMapping("/getUsersByGroup")
  @ResponseBody
  public UsersDto getUsersByGroup(@RequestParam String code, HttpSession session) {
    Group group = groupService.getGroupByCode(code);
    List<User> users = userService.getUsersByGroup(group);
    UsersDto usersDto = new UsersDto();
    usersDto.setUsers(users.stream()
            .map(user -> {
              UserDto userDto = new UserDto();
              userDto.setLogin(user.getLogin());
              userDto.setName(user.getName());
              userDto.setMiddleName(user.getMiddleName());
              userDto.setSurname(user.getSurname());
              userDto.setPassword(user.getPassword());
              return userDto;
            }).collect(Collectors.toList()));
    addRoleAndId(usersDto, session);
    return usersDto;
  }

  @DeleteMapping("/deleteUser")
  @ResponseBody
  public MessageDto deleteUser(@RequestBody @Valid LoginWrapper loginWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      userService.deleteByLogin(loginWrapper.getLogin());
    }
    return createUserAdminDto(successful, bindingResult, DELETE_SUCCESS_MESSAGE, session);
  }

  @PutMapping("/updateUser")
  @ResponseBody
  public MessageDto updateUser(@RequestBody @Valid UserFullWrapper userAdminWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User user = createUser(userAdminWrapper);
      userService.update(user);
    }
    return createUserAdminDto(successful, bindingResult, UPDATE_SUCCESS_MESSAGE, session);
  }

  @PostMapping("/addUser")
  @ResponseBody
  public MessageDto addUser(@RequestBody @Valid UserFullWrapper userAdminWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User user = createUser(userAdminWrapper);
      userService.save(user);
    }
    return createUserAdminDto(successful, bindingResult, ADD_SUCCESS_MESSAGE, session);
  }

  @PostMapping("/addGroup")
  @ResponseBody
  public MessageDto addGroup(@RequestBody @Valid GroupWrapper groupWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      Group group = new Group();
      group.setCode(groupWrapper.getCode());
      groupService.save(group);
    }
    return createUserAdminDto(successful, bindingResult, ADD_SUCCESS_MESSAGE, session);
  }

  private User createUser(UserFullWrapper userAdminWrapper) {
    User user = new User();
    user.setName(userAdminWrapper.getName());
    user.setSurname(userAdminWrapper.getSurname());
    user.setMiddleName(userAdminWrapper.getMiddleName());
    user.setLogin(userAdminWrapper.getLogin());
    user.setPassword(userAdminWrapper.getPassword());
    user.setRole(Roles.STUDENT.name());
    user.setGroup(groupService.getGroupByCode(userAdminWrapper.getGroupCode()));
    return user;
  }

  private MessageDto createUserAdminDto(boolean successful, BindingResult bindingResult, String successMessage, HttpSession session) {
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
    addRoleAndId(userAdminDto, session);
    return userAdminDto;
  }

  private void addRoleAndId(AbstractDto dto, HttpSession session) {
    dto.setUser_role((String) session.getAttribute("user_role"));
    dto.setUser_id((Long) session.getAttribute("user_id"));
  }
}
