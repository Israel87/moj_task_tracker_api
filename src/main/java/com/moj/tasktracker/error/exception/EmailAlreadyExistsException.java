package com.moj.tasktracker.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExistsException extends RuntimeException {

  private static final String code = "conflict";

  public EmailAlreadyExistsException(String msg) {
    super(msg);
  }

  public String getCode() {
    return code;
  }
  
}
