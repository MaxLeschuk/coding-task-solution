package com.coding.task.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

  private String errorCode;
  private String message;
  private Integer status;
}
