package com.mjsamaha.codelobster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mjsamaha.codelobster.security.CustomUserDetailsService;
import com.mjsamaha.codelobster.security.JwtAccessDeniedHandler;
import com.mjsamaha.codelobster.security.JwtAuthEntryPoint;
import com.mjsamaha.codelobster.security.JwtAuthenticationFilter;
import com.mjsamaha.codelobster.user.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtAuthEntryPoint jwtAuthEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
            CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // Public
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/problems/public/**")
                        .permitAll()

                        // User and above
                        .requestMatchers("/api/submissions/**")
                        .hasAnyAuthority(
                                UserRole.USER.getAuthority(),
                                UserRole.MODERATOR.getAuthority(),
                                UserRole.PROBLEM_SETTER.getAuthority(),
                                UserRole.CONTEST_MANAGER.getAuthority(),
                                UserRole.ADMIN.getAuthority())

                        // Moderator and admin
                        .requestMatchers("/api/admin/reports/**")
                        .hasAnyAuthority(
                                UserRole.MODERATOR.getAuthority(),
                                UserRole.ADMIN.getAuthority())

                        // Problem setter and admin
                        .requestMatchers("/api/admin/problems/**")
                        .hasAnyAuthority(
                                UserRole.PROBLEM_SETTER.getAuthority(),
                                UserRole.ADMIN.getAuthority())

                        // Contest manager and admin
                        .requestMatchers("/api/admin/contests/**")
                        .hasAnyAuthority(
                                UserRole.CONTEST_MANAGER.getAuthority(),
                                UserRole.ADMIN.getAuthority())

                        // Admin only
                        .requestMatchers("/api/admin/users/**")
                        .hasAuthority(UserRole.ADMIN.getAuthority())

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
