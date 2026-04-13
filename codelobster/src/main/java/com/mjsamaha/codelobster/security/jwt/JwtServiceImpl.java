package com.mjsamaha.codelobster.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjsamaha.codelobster.user.User;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String HMAC_ALGO = "HmacSHA256";
    private static final Base64.Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder BASE64_URL_DECODER = Base64.getUrlDecoder();
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {
    };

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    public JwtServiceImpl(JwtProperties jwtProperties, ObjectMapper objectMapper) {
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateAccessToken(User user) {
        return generateToken(user, JwtTokenType.ACCESS, jwtProperties.getAccessTokenExpirationSeconds());
    }

    @Override
    public String generateRefreshToken(User user) {
        return generateToken(user, JwtTokenType.REFRESH, jwtProperties.getRefreshTokenExpirationSeconds());
    }

    @Override
    public Optional<JwtTokenClaims> parseAndValidate(String token) {
        try {
            if (token == null || token.isBlank()) {
                return Optional.empty();
            }

            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return Optional.empty();
            }

            String encodedHeader = parts[0];
            String encodedPayload = parts[1];
            String signature = parts[2];

            String signingInput = encodedHeader + "." + encodedPayload;
            String expectedSignature = sign(signingInput, jwtProperties.getSecret());
            if (!MessageDigest.isEqual(expectedSignature.getBytes(StandardCharsets.UTF_8),
                    signature.getBytes(StandardCharsets.UTF_8))) {
                return Optional.empty();
            }

            Map<String, Object> payload = decodeAsMap(encodedPayload);
            JwtTokenClaims claims = extractClaims(payload);

            if (claims.expiresAt() == null || claims.expiresAt().isBefore(Instant.now())) {
                return Optional.empty();
            }
            if (jwtProperties.getIssuer() != null && !jwtProperties.getIssuer().isBlank()) {
                Object issuerClaim = payload.get("iss");
                if (issuerClaim == null || !jwtProperties.getIssuer().equals(String.valueOf(issuerClaim))) {
                    return Optional.empty();
                }
            }

            return Optional.of(claims);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isTokenType(String token, JwtTokenType expectedType) {
        if (expectedType == null) {
            return false;
        }
        return parseAndValidate(token)
                .map(claims -> expectedType == claims.tokenType())
                .orElse(false);
    }

    private String generateToken(User user, JwtTokenType tokenType, long expirationSeconds) {
        if (user == null) {
            throw new IllegalArgumentException("User is required to generate a token.");
        }
        if (jwtProperties.getSecret() == null || jwtProperties.getSecret().isBlank()) {
            throw new IllegalStateException("JWT secret is not configured.");
        }
        if (expirationSeconds <= 0) {
            throw new IllegalStateException("JWT expiration must be positive.");
        }

        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(expirationSeconds);

        Map<String, Object> header = new LinkedHashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("iss", jwtProperties.getIssuer());
        payload.put("sub", user.getUsername());
        payload.put("jti", UUID.randomUUID().toString());
        payload.put("iat", now.getEpochSecond());
        payload.put("exp", expiresAt.getEpochSecond());
        payload.put("uid", user.getId());
        payload.put("username", user.getUsername());
        payload.put("role", user.getRole().name());
        payload.put("token_type", tokenType.name());

        String encodedHeader = encodeAsJson(header);
        String encodedPayload = encodeAsJson(payload);
        String signingInput = encodedHeader + "." + encodedPayload;
        String signature = sign(signingInput, jwtProperties.getSecret());

        return signingInput + "." + signature;
    }

    private JwtTokenClaims extractClaims(Map<String, Object> payload) {
        String subject = asString(payload.get("sub"));
        Long userId = asLong(payload.get("uid"));
        String username = asString(payload.get("username"));
        String role = asString(payload.get("role"));
        JwtTokenType tokenType = toTokenType(asString(payload.get("token_type")));
        Instant issuedAt = toInstant(payload.get("iat"));
        Instant expiresAt = toInstant(payload.get("exp"));

        return new JwtTokenClaims(subject, userId, username, role, tokenType, issuedAt, expiresAt, payload);
    }

    private String encodeAsJson(Map<String, Object> source) {
        try {
            byte[] jsonBytes = objectMapper.writeValueAsBytes(source);
            return BASE64_URL_ENCODER.encodeToString(jsonBytes);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to encode JWT JSON.", ex);
        }
    }

    private Map<String, Object> decodeAsMap(String base64Part) {
        try {
            byte[] decoded = BASE64_URL_DECODER.decode(base64Part);
            return objectMapper.readValue(decoded, MAP_TYPE);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to decode JWT payload.", ex);
        }
    }

    private String sign(String signingInput, String secret) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGO);
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
            mac.init(keySpec);
            byte[] signatureBytes = mac.doFinal(signingInput.getBytes(StandardCharsets.UTF_8));
            return BASE64_URL_ENCODER.encodeToString(signatureBytes);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to sign JWT token.", ex);
        }
    }

    private String asString(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private Long asLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        return Long.parseLong(String.valueOf(value));
    }

    private Instant toInstant(Object epochValue) {
        Long epochSeconds = asLong(epochValue);
        return epochSeconds == null ? null : Instant.ofEpochSecond(epochSeconds);
    }

    private JwtTokenType toTokenType(String rawType) {
        if (rawType == null || rawType.isBlank()) {
            return null;
        }
        try {
            return JwtTokenType.valueOf(rawType);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
