package com.monkeybusiness.diplom.web.controller.pages;

import com.monkeybusiness.core.model.document.Document;
import com.monkeybusiness.core.model.practice.Practice;
import com.monkeybusiness.core.model.service.DocumentService;
import com.monkeybusiness.core.model.service.PracticeService;
import com.monkeybusiness.core.model.service.UserService;
import com.monkeybusiness.core.model.user.User;
import com.monkeybusiness.diplom.web.controller.dto.OrganizatorDto;
import com.monkeybusiness.diplom.web.controller.validation.DocumentWrapper;
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
@RequestMapping("/orgprakt")
public class OrgpraktController {
  public static final String DELIMITER = "\n";
  public static final String ADD_SUCCESS_MESSAGE = "Added successfully";
  public static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully";
  public static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully";

  @Autowired
  private DocumentService documentService;

  @Autowired
  private PracticeService practiceService;

  @Autowired
  private UserService userService;

  @GetMapping("/practices")
  @ResponseBody
  public List<Practice> getPractices() {
    return practiceService.getAllPractices();
  }

  @DeleteMapping
  @ResponseBody
  public OrganizatorDto deletePractice(@RequestBody @Valid IdWrapper idWrapper, BindingResult bindingResult) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      practiceService.delete(idWrapper.getId());
    }
    return createOrganizatorDto(successful, bindingResult, DELETE_SUCCESS_MESSAGE);
  }

  @PutMapping
  @ResponseBody
  public OrganizatorDto updatePractice(@RequestBody @Valid PracticeWrapper practiceWrapper, BindingResult bindingResult) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      Practice practice = new Practice();
      practice.setId(practiceWrapper.getId());
      practice.setPracticeDateStart(practiceWrapper.getPracticeDateStart());
      practice.setPracticeDateEnd(practiceWrapper.getPracticeDateEnd());
      practice.setLocation(practiceWrapper.getLocation());
      practice.setStatus(practiceWrapper.getStatus());
      practiceService.update(practice);
    }
    return createOrganizatorDto(successful, bindingResult, UPDATE_SUCCESS_MESSAGE);
  }

  @PostMapping
  @ResponseBody
  public OrganizatorDto addPractice(@RequestBody @Valid PracticeWrapper practiceWrapper, BindingResult bindingResult) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      Practice practice = new Practice();
      practice.setId(practiceWrapper.getId());
      practice.setPracticeDateStart(practiceWrapper.getPracticeDateStart());
      practice.setPracticeDateEnd(practiceWrapper.getPracticeDateEnd());
      practice.setLocation(practiceWrapper.getLocation());
      practice.setStatus(practiceWrapper.getStatus());
      practiceService.update(practice);
    }
    return createOrganizatorDto(successful, bindingResult, ADD_SUCCESS_MESSAGE);
  }

  @PostMapping("/addToGroup")
  @ResponseBody
  public OrganizatorDto addUserToGroup(@RequestBody @Valid UserFullWrapper userFullWrapper, BindingResult bindingResult) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User user = new User();
      user.setUsername(userFullWrapper.getUsername());
      user.setPassword(userFullWrapper.getPassword());
      user.setRole(userFullWrapper.getRole());
      user.setPracticeId(userFullWrapper.getPracticeId());
      user.setGroup(userFullWrapper.getGroup());
      userService.save(user);
    }
    return createOrganizatorDto(successful, bindingResult, ADD_SUCCESS_MESSAGE);
  }

  @PostMapping("/addDocumentStatus")
  @ResponseBody
  public OrganizatorDto addDocumentStatus(@RequestBody @Valid DocumentWrapper documentWrapper, BindingResult bindingResult) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      Document document = new Document();
      document.setId(documentWrapper.getId());
      document.setPath(documentWrapper.getPath());
      document.setUserId(documentWrapper.getUserId());
      document.setDocumentStatus(documentWrapper.getStatus());
      documentService.save(document);
    }
    return createOrganizatorDto(successful, bindingResult, ADD_SUCCESS_MESSAGE);
  }

  private OrganizatorDto createOrganizatorDto(boolean successful, BindingResult bindingResult, String successMessage) {
    OrganizatorDto organizatorDto = new OrganizatorDto();
    organizatorDto.setSuccessful(successful);
    String message;
    if (!successful) {
      message = bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(DELIMITER));
    } else {
      message = successMessage;
    }
    organizatorDto.setMessage(message);
    return organizatorDto;
  }
}
