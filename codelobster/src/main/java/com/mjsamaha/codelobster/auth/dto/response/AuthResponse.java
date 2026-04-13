package com.mjsamaha.codelobster.auth.dto.response;

public record AuthResponse(
        String tokenType,
        String accessToken,
        String refreshToken,
        long accessTokenExpiresInSeconds,
        Long userId,
        String username,
        String role) {
}
