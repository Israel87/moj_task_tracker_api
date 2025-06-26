package com.moj.tasktracker.dto.Auth;

import lombok.Data;

@Data
public class AuthResponse {

  private String token;
  private String refreshToken;

}
