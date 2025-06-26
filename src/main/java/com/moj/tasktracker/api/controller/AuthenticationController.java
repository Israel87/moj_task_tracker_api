package com.moj.tasktracker.api.controller;

import com.moj.tasktracker.api.model.User;
import com.moj.tasktracker.dto.Auth.AuthResponse;
import com.moj.tasktracker.dto.Auth.LoginRequest;
import com.moj.tasktracker.dto.Auth.RefreshTokenRequest;
import com.moj.tasktracker.dto.Auth.SignUpRequest;
import com.moj.tasktracker.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthService authService;

  @Operation(summary = "Register", description = "create a new user")
  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody @Validated SignUpRequest request) {
    return ResponseEntity.ok(authService.signUp(request));
  }

  @Operation(summary = "Login", description = "login to service")
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(
      @RequestBody @Validated LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @Operation(summary = "Refresh Token", description = "refresh token")
  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(
      @RequestBody @Validated RefreshTokenRequest request) {
    return ResponseEntity.ok(authService.refreshToken(request));
  }

}
