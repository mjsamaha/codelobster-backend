package com.mjsamaha.codelobster.auth;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjsamaha.codelobster.common.exception.EmailAlreadyUsedException;
import com.mjsamaha.codelobster.common.exception.ForbiddenException;
import com.mjsamaha.codelobster.common.exception.InvalidTokenException;
import com.mjsamaha.codelobster.common.exception.UnauthorizedException;
import com.mjsamaha.codelobster.common.exception.UsernameTakenException;
import com.mjsamaha.codelobster.security.jwt.JwtService;
import com.mjsamaha.codelobster.security.jwt.JwtTokenClaims;
import com.mjsamaha.codelobster.security.jwt.JwtTokenType;
import com.mjsamaha.codelobster.user.User;
import com.mjsamaha.codelobster.user.UserRepository;
import com.mjsamaha.codelobster.user.UserRole;
import com.mjsamaha.codelobster.user.UserService;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    // Tracks used refresh token JTIs to enforce one-time-use refresh rotation.
    private final Map<String, Long> usedRefreshTokenJtis = new ConcurrentHashMap<>();

    public AuthServiceImpl(
            UserService userService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResult register(String username, String email, String rawPassword) {
        validateCredentialInput(username, email, rawPassword);

        if (!userService.isUsernameAvailable(username)) {
            throw new UsernameTakenException("Username is already taken.");
        }
        if (!userService.isEmailAvailable(email)) {
            throw new EmailAlreadyUsedException("Email is already registered.");
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setEmail(email.trim());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(UserRole.USER);
        user.setEnabled(false);
        user.setEmailVerified(false);
        user.setVerificationToken(UUID.randomUUID().toString());

        User saved = userService.createUser(user);

        // Registration succeeds, but no login tokens are issued until email verification.
        return new AuthResult(
                null,
                null,
                0L,
                "Bearer",
                saved.getId(),
                saved.getUsername(),
                saved.getRole().name());
    }

    @Override
    public AuthResult login(String usernameOrEmail, String rawPassword) {
        if (usernameOrEmail == null || usernameOrEmail.isBlank()) {
            throw new IllegalArgumentException("Username or email is required.");
        }
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }

        String normalized = usernameOrEmail.trim();
        User user = userRepository.findByUsernameIgnoreCase(normalized)
                .or(() -> userRepository.findByEmailIgnoreCase(normalized))
                .orElseThrow(() -> new UnauthorizedException("Invalid username/email or password."));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UnauthorizedException("Invalid username/email or password.");
        }
        if (Boolean.FALSE.equals(user.getEnabled())) {
            throw new ForbiddenException("Account is disabled.");
        }
        if (Boolean.FALSE.equals(user.getEmailVerified())) {
            throw new ForbiddenException("Email is not verified.");
        }
        if (user.getRole() == UserRole.BANNED) {
            throw new ForbiddenException("Account is banned.");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        userService.updateLastLoginAt(user.getId());

        return new AuthResult(
                accessToken,
                refreshToken,
                extractExpiresInSeconds(accessToken),
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getRole().name());
    }

    @Override
    public AuthResult refresh(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Refresh token is required.");
        }

        JwtTokenClaims refreshClaims = jwtService.parseAndValidate(refreshToken.trim())
                .filter(claims -> claims.tokenType() == JwtTokenType.REFRESH)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token."));

        if (refreshClaims.userId() == null) {
            throw new InvalidTokenException("Refresh token missing user id.");
        }
        String refreshJti = extractJti(refreshClaims);
        if (refreshJti == null || refreshJti.isBlank()) {
            throw new InvalidTokenException("Refresh token missing jti.");
        }
        if (isRefreshTokenAlreadyUsed(refreshJti)) {
            throw new InvalidTokenException("Refresh token already used.");
        }

        User user = userService.getByIdOrThrow(refreshClaims.userId());
        if (Boolean.FALSE.equals(user.getEnabled()) || Boolean.FALSE.equals(user.getEmailVerified())
                || user.getRole() == UserRole.BANNED) {
            throw new ForbiddenException("User is not allowed to refresh session.");
        }

        markRefreshTokenAsUsed(refreshJti, refreshClaims.expiresAt());

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return new AuthResult(
                newAccessToken,
                newRefreshToken,
                extractExpiresInSeconds(newAccessToken),
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getRole().name());
    }

    @Override
    public void logout(String refreshToken) {
        // Stateless logout:
        // client should discard both access and refresh tokens.
        // Optional future enhancement: token blacklist/revocation store.
    }

    @Override
    public void verifyEmail(String verificationToken) {
        userService.verifyEmail(verificationToken);
    }

    private void validateCredentialInput(String username, String email, String rawPassword) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
    }

    private long extractExpiresInSeconds(String accessToken) {
        return jwtService.parseAndValidate(accessToken)
                .map(claims -> claims.expiresAt().getEpochSecond() - claims.issuedAt().getEpochSecond())
                .orElse(0L);
    }

    private String extractJti(JwtTokenClaims claims) {
        Object value = claims.rawClaims().get("jti");
        return value == null ? null : String.valueOf(value);
    }

    private boolean isRefreshTokenAlreadyUsed(String jti) {
        cleanupUsedRefreshJtis();
        return usedRefreshTokenJtis.containsKey(jti);
    }

    private void markRefreshTokenAsUsed(String jti, Instant expiresAt) {
        long expirationEpoch = expiresAt == null ? Instant.now().getEpochSecond() : expiresAt.getEpochSecond();
        usedRefreshTokenJtis.put(jti, expirationEpoch);
    }

    private void cleanupUsedRefreshJtis() {
        long now = Instant.now().getEpochSecond();
        usedRefreshTokenJtis.entrySet().removeIf(entry -> entry.getValue() <= now);
    }
}
