package com.moj.tasktracker.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCredentialsException extends RuntimeException {

  private static final String code = "bad_request";

  public InvalidCredentialsException(String message) {
    super(message);
  }

  public String getCode() {
    return code;
  }
}
