package com.moj.tasktracker.service;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String generateToken(UserDetails userDetails);

  String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails);

  String extractUserName(String token);

  boolean isTokenValid(String token, UserDetails userDetails);
}
