package com.monkeybusiness.diplom.web.controller.pages;

import com.monkeybusiness.core.model.document.Document;
import com.monkeybusiness.core.model.document.DocumentStatus;
import com.monkeybusiness.core.model.service.DocumentService;
import com.monkeybusiness.diplom.web.controller.dto.AbstractDto;
import com.monkeybusiness.diplom.web.controller.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
public class FileuploadController {
  public static final String FILE_DESTINATION = "C:\\";
  @Autowired
  private DocumentService documentService;

  @RequestMapping(value = "/fileupload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
  public MessageDto upload(@RequestParam("file") MultipartFile inputFile, HttpSession session) {
    MessageDto messageDto = new MessageDto();
    if (!inputFile.isEmpty()) {
      try {
        String originalFilename = inputFile.getOriginalFilename();
        File destinationFile = new File(FILE_DESTINATION + File.separator + originalFilename);
        inputFile.transferTo(destinationFile);
        Document document = new Document();
        document.setPath(destinationFile.getPath());
        document.setStatus(DocumentStatus.UPLOADED.name());
        document.setUserId((Long) session.getAttribute("userId"));
        documentService.save(document);
        messageDto.setSuccessful(true);
        addRoleAndId(messageDto, session);
        return messageDto;
      } catch (Exception e) {
        messageDto.setSuccessful(false);
        addRoleAndId(messageDto, session);
        return messageDto;
      }
    } else {
      messageDto.setSuccessful(false);
      addRoleAndId(messageDto, session);
      return messageDto;
    }
  }

  @RequestMapping(value = "/download/{id}", headers = ("content-type=multipart/*"), method = RequestMethod.GET)
  public ResponseEntity<Resource> upload(@PathVariable Long id) throws IOException {
    Document document = documentService.getDocument(id);
    Path path = Paths.get(document.getPath());
    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
  }

  private void addRoleAndId(AbstractDto dto, HttpSession session) {
    dto.setUser_role((String) session.getAttribute("user_role"));
    dto.setUser_id((Long) session.getAttribute("user_id"));
  }
}
