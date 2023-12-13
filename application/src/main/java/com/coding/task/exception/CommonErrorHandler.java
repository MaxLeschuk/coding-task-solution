package com.coding.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CommonErrorHandler extends ResponseEntityExceptionHandler {

  private final static String ERROR_REASON_CODE = "INTERNAL_SERVER_ERROR";

  @ExceptionHandler(ReadingFileException.class)
  public ResponseEntity<ErrorDto> handleError(ReadingFileException e,
      WebRequest request) {
    ErrorDto errorDto = new ErrorDto(e.getErrorCode(), e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorDto);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleError(Exception e,
      WebRequest request) {
    ErrorDto errorDto = new ErrorDto(ERROR_REASON_CODE, e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorDto);
  }
}
