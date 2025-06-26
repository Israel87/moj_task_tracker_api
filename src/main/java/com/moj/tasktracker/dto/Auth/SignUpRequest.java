package com.moj.tasktracker.dto.Auth;

import com.moj.tasktracker.validation.constraint.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {

  @NotBlank(message = "First Name is required")
  private String firstName;

  @NotBlank(message = "Last Name is required")
  private String lastName;
  
  @ValidEmail(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Password is required")
  private String password;
}
