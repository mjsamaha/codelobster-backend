package com.mjsamaha.codelobster.user;

import org.springframework.stereotype.Component;

import com.mjsamaha.codelobster.user.dto.response.LeaderboardItemResponse;
import com.mjsamaha.codelobster.user.dto.response.PublicUserProfileResponse;
import com.mjsamaha.codelobster.user.dto.response.UserProfileResponse;
import com.mjsamaha.codelobster.user.dto.response.UserStatsResponse;

@Component
public class UserMapper {

    public UserProfileResponse toUserProfile(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getRating(),
                user.getRank(),
                user.getProblemSolved(),
                user.getContestParticipated(),
                user.getEnabled(),
                user.getEmailVerified(),
                user.getCountry(),
                user.getBio(),
                user.getCreatedAt(),
                user.getLastLoginAt());
    }

    public PublicUserProfileResponse toPublicProfile(User user) {
        return new PublicUserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getRating(),
                user.getRank(),
                user.getProblemSolved(),
                user.getContestParticipated(),
                user.getCountry(),
                user.getBio(),
                user.getCreatedAt());
    }

    public UserStatsResponse toStats(User user) {
        return new UserStatsResponse(
                user.getId(),
                user.getUsername(),
                user.getRating(),
                user.getRank(),
                user.getProblemSolved(),
                user.getContestParticipated());
    }

    public LeaderboardItemResponse toLeaderboardItem(User user, int position) {
        return new LeaderboardItemResponse(
                position,
                user.getId(),
                user.getUsername(),
                user.getRating(),
                user.getRank(),
                user.getProblemSolved(),
                user.getContestParticipated());
    }
}
