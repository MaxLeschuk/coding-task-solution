package com.coding.task.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReadingFileException extends RuntimeException {

  private final String errorCode = "ERROR_DURING_FILE_READING";
  private final String message;
}
