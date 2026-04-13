package com.mjsamaha.codelobster.user.dto.response;

import com.mjsamaha.codelobster.common.Rank;

public record UserStatsResponse(
        Long userId,
        String username,
        Integer rating,
        Rank rank,
        Integer problemSolved,
        Integer contestParticipated) {
}
