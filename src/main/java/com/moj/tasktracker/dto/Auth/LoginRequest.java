package com.moj.tasktracker.dto.Auth;

import com.moj.tasktracker.validation.constraint.ValidEmail;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

  @ValidEmail
  private String email;

  @NotNull(message = "password is required")
  private String password;
}
