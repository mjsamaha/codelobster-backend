package com.mjsamaha.codelobster.user;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mjsamaha.codelobster.common.Country;
import com.mjsamaha.codelobster.security.CustomUserDetails;
import com.mjsamaha.codelobster.user.dto.request.UpdateProfileRequest;
import com.mjsamaha.codelobster.user.dto.response.LeaderboardItemResponse;
import com.mjsamaha.codelobster.user.dto.response.PublicUserProfileResponse;
import com.mjsamaha.codelobster.user.dto.response.UserProfileResponse;
import com.mjsamaha.codelobster.user.dto.response.UserStatsResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userService.getByIdOrThrow(currentUser.getUserId());
        return ResponseEntity.ok(userMapper.toUserProfile(user));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserProfileResponse> updateCurrentUserProfile(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @Valid @RequestBody UpdateProfileRequest request) {
        Country country = parseCountry(request.country());
        User updated = userService.updateProfile(currentUser.getUserId(), request.bio(), country);
        return ResponseEntity.ok(userMapper.toUserProfile(updated));
    }

    @GetMapping("/{username}")
    public ResponseEntity<PublicUserProfileResponse> getUserPublicProfile(@PathVariable String username) {
        User user = userService.getByUsernameOrThrow(username);
        return ResponseEntity.ok(userMapper.toPublicProfile(user));
    }

    @GetMapping("/{username}/stats")
    public ResponseEntity<UserStatsResponse> getUserStats(@PathVariable String username) {
        User user = userService.getByUsernameOrThrow(username);
        return ResponseEntity.ok(userMapper.toStats(user));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardItemResponse>> getLeaderboard(
            @RequestParam(defaultValue = "50") @Min(1) @Max(200) int limit) {
        AtomicInteger position = new AtomicInteger(1);
        List<LeaderboardItemResponse> leaderboard = userService.getTopUsers(limit)
                .stream()
                .map(user -> userMapper.toLeaderboardItem(user, position.getAndIncrement()))
                .toList();
        return ResponseEntity.ok(leaderboard);
    }

    private Country parseCountry(String countryValue) {
        if (countryValue == null || countryValue.isBlank()) {
            return null;
        }
        try {
            return Country.valueOf(countryValue.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid country code: " + countryValue);
        }
    }
}
