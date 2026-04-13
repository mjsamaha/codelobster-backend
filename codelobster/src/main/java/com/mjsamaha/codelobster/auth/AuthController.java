package com.mjsamaha.codelobster.auth;

import com.mjsamaha.codelobster.auth.dto.request.LoginRequest;
import com.mjsamaha.codelobster.auth.dto.request.RefreshTokenRequest;
import com.mjsamaha.codelobster.auth.dto.request.RegisterRequest;
import com.mjsamaha.codelobster.auth.dto.response.AuthResponse;
import com.mjsamaha.codelobster.common.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthService.AuthResult result = authService.register(
                request.username().trim(),
                request.email().trim(),
                request.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseBody(result));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthService.AuthResult result = authService.login(
                request.usernameOrEmail().trim(),
                request.password());
        return ResponseEntity.ok(toResponseBody(result));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthService.AuthResult result = authService.refresh(request.refreshToken().trim());
        return ResponseEntity.ok(toResponseBody(result));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestBody(required = false) RefreshTokenRequest request) {
        if (request != null && request.refreshToken() != null && !request.refreshToken().isBlank()) {
            authService.logout(request.refreshToken().trim());
        }
        return ResponseEntity.ok(new MessageResponse("Logged out successfully."));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<MessageResponse> verifyEmail(@RequestParam("token") String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(new MessageResponse("Email verified successfully."));
    }

    private AuthResponse toResponseBody(AuthService.AuthResult result) {
        return new AuthResponse(
                result.tokenType(),
                result.accessToken(),
                result.refreshToken(),
                result.accessTokenExpiresInSeconds(),
                result.userId(),
                result.username(),
                result.role());
    }
}
