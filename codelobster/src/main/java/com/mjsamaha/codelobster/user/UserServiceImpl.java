package com.mjsamaha.codelobster.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjsamaha.codelobster.common.Country;
import com.mjsamaha.codelobster.common.exception.EmailAlreadyUsedException;
import com.mjsamaha.codelobster.common.exception.InvalidTokenException;
import com.mjsamaha.codelobster.common.exception.UserNotFoundException;
import com.mjsamaha.codelobster.common.exception.UsernameTakenException;
import com.mjsamaha.codelobster.rating.RankService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RankService rankService;

    public UserServiceImpl(UserRepository userRepository, RankService rankService) {
        this.userRepository = userRepository;
        this.rankService = rankService;
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        String normalizedUsername = normalize(user.getUsername());
        String normalizedEmail = normalize(user.getEmail());

        if (normalizedUsername == null || normalizedUsername.isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (normalizedEmail == null || normalizedEmail.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
        if (userRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
            throw new UsernameTakenException("Username is already taken.");
        }
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new EmailAlreadyUsedException("Email is already registered.");
        }

        user.setUsername(normalizedUsername);
        user.setEmail(normalizedEmail);

        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }
        if (user.getRating() == null || user.getRating() < 0) {
            user.setRating(0);
        }
        if (user.getProblemSolved() == null || user.getProblemSolved() < 0) {
            user.setProblemSolved(0);
        }
        if (user.getContestParticipated() == null || user.getContestParticipated() < 0) {
            user.setContestParticipated(0);
        }
        if (user.getEnabled() == null) {
            user.setEnabled(true);
        }
        if (user.getEmailVerified() == null) {
            user.setEmailVerified(false);
        }
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        user.setRank(rankService.calculateRank(user));
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(normalize(username));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(normalize(email));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsernameOrThrow(String username) {
        return userRepository.findByUsernameIgnoreCase(normalize(username))
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUsernameAvailable(String username) {
        String normalizedUsername = normalize(username);
        return normalizedUsername != null
                && !normalizedUsername.isBlank()
                && !userRepository.existsByUsernameIgnoreCase(normalizedUsername);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        String normalizedEmail = normalize(email);
        return normalizedEmail != null
                && !normalizedEmail.isBlank()
                && !userRepository.existsByEmailIgnoreCase(normalizedEmail);
    }

    @Override
    public User updateProfile(Long userId, String bio, Country country) {
        User user = getByIdOrThrow(userId);
        user.setBio(bio != null ? bio.trim() : null);
        user.setCountry(country);
        return userRepository.save(user);
    }

    @Override
    public User updateRole(Long userId, UserRole newRole) {
        if (newRole == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }

        User user = getByIdOrThrow(userId);
        user.setRole(newRole);
        return userRepository.save(user);
    }

    @Override
    public User setEnabled(Long userId, boolean enabled) {
        User user = getByIdOrThrow(userId);
        user.setEnabled(enabled);
        return userRepository.save(user);
    }

    @Override
    public User verifyEmail(String verificationToken) {
        if (verificationToken == null || verificationToken.isBlank()) {
            throw new IllegalArgumentException("Verification token is required.");
        }

        User user = userRepository.findByVerificationToken(verificationToken.trim())
                .orElseThrow(() -> new InvalidTokenException("Invalid verification token."));

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public void updateLastLoginAt(Long userId) {
        User user = getByIdOrThrow(userId);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public User updateRating(Long userId, int newRating, int globalRank) {
        if (newRating < 0) {
            throw new IllegalArgumentException("Rating cannot be negative.");
        }

        User user = getByIdOrThrow(userId);
        user.setRating(newRating);
        user.setRank(rankService.calculateRank(user, globalRank));
        return userRepository.save(user);
    }

    @Override
    public User incrementProblemSolved(Long userId) {
        User user = getByIdOrThrow(userId);

        int currentSolved = user.getProblemSolved() == null ? 0 : user.getProblemSolved();
        user.setProblemSolved(currentSolved + 1);
        user.setRank(rankService.calculateRank(user));

        return userRepository.save(user);
    }

    @Override
    public User incrementContestParticipated(Long userId) {
        User user = getByIdOrThrow(userId);

        int currentContests = user.getContestParticipated() == null ? 0 : user.getContestParticipated();
        user.setContestParticipated(currentContests + 1);
        user.setRank(rankService.calculateRank(user));

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getTopUsers(int limit) {
        int safeLimit = Math.max(1, limit);
        return userRepository.findLeaderboard(PageRequest.of(0, safeLimit)).getContent();
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
