package com.mjsamaha.codelobster.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mjsamaha.codelobster.user.User;
import com.mjsamaha.codelobster.user.UserRole;

public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String username;
    private final String email;
    private final String password;
    private final UserRole role;
    private final boolean enabled;
    private final boolean emailVerified;

    public CustomUserDetails(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.enabled = Boolean.TRUE.equals(user.getEnabled());
        this.emailVerified = Boolean.TRUE.equals(user.getEmailVerified());
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return role != UserRole.BANNED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled && emailVerified;
    }
}
