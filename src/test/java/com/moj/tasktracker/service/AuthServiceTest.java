package com.moj.tasktracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import com.moj.tasktracker.api.model.Role;
import com.moj.tasktracker.api.model.User;
import com.moj.tasktracker.dto.Auth.AuthResponse;
import com.moj.tasktracker.dto.Auth.LoginRequest;
import com.moj.tasktracker.dto.Auth.RefreshTokenRequest;
import com.moj.tasktracker.dto.Auth.SignUpRequest;
import com.moj.tasktracker.error.exception.EmailAlreadyExistsException;
import com.moj.tasktracker.repository.UserRepository;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthServiceTest {

  private static final String VALID_EMAIL = "user@mail.com";
  private static final String PASSWORD = "password";
  private static final String ENCODED_PASSWORD = "encodedPassword";
  private static final String JWT_TOKEN = "jwtToken";
  private static final String REFRESH_TOKEN = "refreshToken";
  private static final String NEW_TOKEN = "newToken";

  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private JwtService jwtService;
  @InjectMocks
  private AuthService authService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void signUp_ShouldCreateUser_WhenEmailNotExists() {
    // arrange
    SignUpRequest request = createSignUpRequest();
    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(passwordEncoder.encode(request.getPassword())).thenReturn(ENCODED_PASSWORD);
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

    // act
    User user = authService.signUp(request);

    // assert
    assertEquals(request.getEmail(), user.getEmail());
    assertEquals(Role.USER, user.getRole());
    assertNotNull(user.getCreatedOn());
  }

  @Test
  void signUp_ShouldThrowException_WhenEmailExists() {
    // arrange
    SignUpRequest request = createSignUpRequest();
    when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

    // act + assert
    assertThrows(EmailAlreadyExistsException.class, () -> authService.signUp(request));
  }

  @Test
  void login_ShouldReturnAuthResponse_WhenCredentialsAreValid() {
    // arrange
    LoginRequest loginRequest = createLoginRequest();
    User user = createUser();

    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
    when(jwtService.generateToken(user)).thenReturn(JWT_TOKEN);
    when(jwtService.generateRefreshToken(any(HashMap.class), eq(user))).thenReturn(REFRESH_TOKEN);

    // act
    AuthResponse response = authService.login(loginRequest);

    // assert
    assertEquals(JWT_TOKEN, response.getToken());
    assertEquals(REFRESH_TOKEN, response.getRefreshToken());
  }

  @Test
  void login_ShouldThrowException_WhenUserNotFound() {
    // arrange
    LoginRequest loginRequest = createLoginRequest();
    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

    // act + assert
    assertThrows(IllegalArgumentException.class, () -> authService.login(loginRequest));
  }

  @Test
  void refreshToken_ShouldReturnNewToken_WhenValidTokenProvided() {
    // arrange
    String token = REFRESH_TOKEN;
    User user = createUser();

    when(jwtService.extractUserName(token)).thenReturn(VALID_EMAIL);
    when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(user));
    when(jwtService.isTokenValid(token, user)).thenReturn(true);
    when(jwtService.generateToken(user)).thenReturn(NEW_TOKEN);

    RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
    refreshTokenRequest.setToken(token);

    // act
    AuthResponse response = authService.refreshToken(refreshTokenRequest);

    // assert
    assertEquals(NEW_TOKEN, response.getToken());
    assertEquals(token, response.getRefreshToken());
  }

  @Test
  void refreshToken_ShouldReturnNull_WhenTokenIsInvalid() {
    // arrange
    String token = "invalidToken";
    User user = createUser();

    when(jwtService.extractUserName(token)).thenReturn(VALID_EMAIL);
    when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(user));
    when(jwtService.isTokenValid(token, user)).thenReturn(false);

    RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
    refreshTokenRequest.setToken(token);

    // act
    AuthResponse response = authService.refreshToken(refreshTokenRequest);

    // assert
    assertNull(response);
  }

  private SignUpRequest createSignUpRequest() {
    SignUpRequest request = new SignUpRequest();
    request.setEmail(VALID_EMAIL);
    request.setPassword(PASSWORD);
    request.setFirstName("Test");
    request.setLastName("Instance");
    return request;
  }

  private LoginRequest createLoginRequest() {
    LoginRequest request = new LoginRequest();
    request.setEmail(VALID_EMAIL);
    request.setPassword(PASSWORD);
    return request;
  }

  private User createUser() {
    User user = new User();
    user.setEmail(VALID_EMAIL);
    return user;
  }
}
