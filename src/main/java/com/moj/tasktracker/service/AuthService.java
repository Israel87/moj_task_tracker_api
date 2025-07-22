package com.moj.tasktracker.service;

import com.moj.tasktracker.api.model.Role;
import com.moj.tasktracker.api.model.User;
import com.moj.tasktracker.dto.Auth.AuthResponse;
import com.moj.tasktracker.dto.Auth.LoginRequest;
import com.moj.tasktracker.dto.Auth.RefreshTokenRequest;
import com.moj.tasktracker.dto.Auth.SignUpRequest;
import com.moj.tasktracker.error.exception.EmailAlreadyExistsException;
import com.moj.tasktracker.error.exception.InvalidCredentialsException;
import com.moj.tasktracker.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository repository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private final JwtService jwtService;

  public User signUp(SignUpRequest request) {
    if (repository.existsByEmail(request.getEmail())) {
      throw new EmailAlreadyExistsException("email already exists");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setRole(Role.USER);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setCreatedOn(LocalDateTime.now());
    user.setCreatedBy("System");

    return repository.save(user);
  }

  public AuthResponse login(LoginRequest loginRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getEmail(),
              loginRequest.getPassword()
          )
      );
    } catch (BadCredentialsException ex) {
      throw new InvalidCredentialsException("Invalid email or password");
    }

    var user = repository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

    var jwt = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

    AuthResponse authResponse = new AuthResponse();
    authResponse.setToken(jwt);
    authResponse.setRefreshToken(refreshToken);
    return authResponse;
  }


  public AuthResponse refreshToken(RefreshTokenRequest request) {
    String userEmail = jwtService.extractUserName(request.getToken());
    User user = repository.findByEmail(userEmail).orElseThrow();
    if (jwtService.isTokenValid(request.getToken(), user)) {
      var jwt = jwtService.generateToken(user);
      AuthResponse authResponse = new AuthResponse();
      authResponse.setToken(jwt);
      authResponse.setRefreshToken(request.getToken());
      return authResponse;
    }

    return null;
  }
}
