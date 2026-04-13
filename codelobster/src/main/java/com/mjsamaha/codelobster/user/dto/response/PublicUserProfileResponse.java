package com.mjsamaha.codelobster.user.dto.response;

import java.time.LocalDateTime;

import com.mjsamaha.codelobster.common.Country;
import com.mjsamaha.codelobster.common.Rank;

public record PublicUserProfileResponse(
        Long id,
        String username,
        Integer rating,
        Rank rank,
        Integer problemSolved,
        Integer contestParticipated,
        Country country,
        String bio,
        LocalDateTime createdAt) {
}
