package com.mjsamaha.codelobster.user;

import java.util.List;
import java.util.Optional;

import com.mjsamaha.codelobster.common.Country;

public interface UserService {

    User createUser(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User getByIdOrThrow(Long userId);

    User getByUsernameOrThrow(String username);

    boolean isUsernameAvailable(String username);

    boolean isEmailAvailable(String email);

    User updateProfile(Long userId, String bio, Country country);

    User updateRole(Long userId, UserRole newRole);

    User setEnabled(Long userId, boolean enabled);

    User verifyEmail(String verificationToken);

    void updateLastLoginAt(Long userId);

    User updateRating(Long userId, int newRating, int globalRank);

    User incrementProblemSolved(Long userId);

    User incrementContestParticipated(Long userId);

    List<User> getTopUsers(int limit);
}
