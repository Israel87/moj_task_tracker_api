package com.moj.tasktracker.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

  private static final String code = "not_found";

  public NotFoundException(String msg) {
    super(msg);
  }

  public String getCode() {
    return code;
  }

}