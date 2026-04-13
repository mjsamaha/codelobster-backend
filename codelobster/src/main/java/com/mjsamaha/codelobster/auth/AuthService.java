package com.mjsamaha.codelobster.auth;

public interface AuthService {

    AuthResult register(String username, String email, String rawPassword);

    AuthResult login(String usernameOrEmail, String rawPassword);

    AuthResult refresh(String refreshToken);

    void logout(String refreshToken);

    void verifyEmail(String verificationToken);

    record AuthResult(
            String accessToken,
            String refreshToken,
            long accessTokenExpiresInSeconds,
            String tokenType,
            Long userId,
            String username,
            String role) {
    }
}
