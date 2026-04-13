package com.mjsamaha.codelobster.user.dto.response;

import java.time.LocalDateTime;

import com.mjsamaha.codelobster.common.Country;
import com.mjsamaha.codelobster.common.Rank;
import com.mjsamaha.codelobster.user.UserRole;

public record UserProfileResponse(
        Long id,
        String username,
        String email,
        UserRole role,
        Integer rating,
        Rank rank,
        Integer problemSolved,
        Integer contestParticipated,
        Boolean enabled,
        Boolean emailVerified,
        Country country,
        String bio,
        LocalDateTime createdAt,
        LocalDateTime lastLoginAt) {
}
