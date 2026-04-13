package com.mjsamaha.codelobster.security.jwt;

import java.time.Instant;
import java.util.Map;

public record JwtTokenClaims(
        String subject,
        Long userId,
        String username,
        String role,
        JwtTokenType tokenType,
        Instant issuedAt,
        Instant expiresAt,
        Map<String, Object> rawClaims) {
}
