package com.mjsamaha.codelobster.security.jwt;

import java.util.Optional;

import com.mjsamaha.codelobster.user.User;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    Optional<JwtTokenClaims> parseAndValidate(String token);

    boolean isTokenType(String token, JwtTokenType expectedType);
}
