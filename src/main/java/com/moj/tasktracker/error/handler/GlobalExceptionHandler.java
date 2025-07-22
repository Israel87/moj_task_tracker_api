package com.moj.tasktracker.error.handler;

import com.moj.tasktracker.error.exception.EmailAlreadyExistsException;
import com.moj.tasktracker.error.exception.InvalidCredentialsException;
import com.moj.tasktracker.error.exception.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public Map<String, String> handleNotFoundException(NotFoundException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("code", ex.getCode());
    errorResponse.put("message", ex.getMessage());
    return errorResponse;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage()));
    return errors;
  }

  @ResponseStatus(value = HttpStatus.CONFLICT)
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public Map<String, String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", ex.getCode());
    errorResponse.put("message", ex.getMessage());
    return errorResponse;
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidCredentialsException.class)
  public Map<String, String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", ex.getCode());
    errorResponse.put("message", ex.getMessage());
    return errorResponse;
  }

}
