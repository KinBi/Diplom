package com.monkeybusiness.diplom.web.controller.pages;

import com.monkeybusiness.core.model.service.UserService;
import com.monkeybusiness.core.model.user.User;
import com.monkeybusiness.diplom.web.controller.dto.UserDto;
import com.monkeybusiness.diplom.web.controller.validation.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/login")
public class LoginController {
  public static final String DELIMITER = "\n";
  public static final String BAD_PASSWORD_MESSAGE = "Bad password";
  public static final String USER_ID_SESSION_ATTRIBUTE = "userId";

  @Autowired
  private UserService userService;

  @GetMapping
  public void la() {
    // todo
  }

  @PostMapping
  @ResponseBody
  public UserDto login(@RequestBody @Valid UserWrapper userWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User validUser = userService.getUserByUsername(userWrapper.getUsername());
      if (!validUser.getPassword().equals(userWrapper.getPassword())) {
        successful = false;
        bindingResult.addError(new ObjectError(User.class.toString(), BAD_PASSWORD_MESSAGE));
      } else {
        session.setAttribute(USER_ID_SESSION_ATTRIBUTE, validUser.getId());
      }
    }
    return createLoginDto(successful, bindingResult);
  }

  private UserDto createLoginDto(boolean successful, BindingResult bindingResult) {
    UserDto UserDto = new UserDto();
    UserDto.setSuccessful(successful);
    if (!successful) {
      UserDto.setMessage(bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(DELIMITER)));
    }
    return UserDto;
  }
}
