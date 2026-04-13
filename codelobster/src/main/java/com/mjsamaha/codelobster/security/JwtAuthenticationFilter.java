package com.mjsamaha.codelobster.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mjsamaha.codelobster.security.jwt.JwtService;
import com.mjsamaha.codelobster.security.jwt.JwtTokenClaims;
import com.mjsamaha.codelobster.security.jwt.JwtTokenType;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private static final List<String> PUBLIC_PREFIXES = List.of(
            "/api/auth/",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-ui.html");

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        for (String prefix : PUBLIC_PREFIXES) {
            if (path.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);
            if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                String token = authHeader.substring(BEARER_PREFIX.length()).trim();
                JwtTokenClaims claims = jwtService.parseAndValidate(token)
                        .filter(parsed -> parsed.tokenType() == JwtTokenType.ACCESS)
                        .orElse(null);

                if (claims != null) {
                    String principalKey = claims.username() != null && !claims.username().isBlank()
                            ? claims.username()
                            : claims.subject();
                    if (principalKey != null && !principalKey.isBlank()) {
                        CustomUserDetails userDetails =
                                (CustomUserDetails) customUserDetailsService.loadUserByUsername(principalKey);
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (Exception ignored) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
