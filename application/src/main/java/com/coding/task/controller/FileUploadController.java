package com.coding.task.controller;

import com.coding.task.exception.ReadingFileException;
import com.coding.task.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Upload file endpoints")
@RestController
@RequiredArgsConstructor
public class FileUploadController {

  private final FileUploadService fileUploadService;
  private final Logger logger = Logger.getLogger(FileUploadController.class.getName());

  @Operation(summary = "Upload file", description = "Content-type multipart/form-data",
      responses = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "500", description = "Internal server error occurred")})
  @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      fileUploadService.persistData(file.getInputStream());
    } catch (IOException e) {
      logger.warning(e.getLocalizedMessage());
      throw new ReadingFileException(e.getLocalizedMessage());
    }
    return ResponseEntity.ok().build();
  }


  @PostMapping(value = "/uploads",
      consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
  public ResponseEntity<Void> uploadLarge(final HttpServletRequest request) {
    try {
      fileUploadService.persistData(request.getInputStream());
    } catch (IOException e) {
      logger.warning(e.getLocalizedMessage());
      throw new ReadingFileException(e.getLocalizedMessage());
    }
    return ResponseEntity.ok().build();
  }
}
