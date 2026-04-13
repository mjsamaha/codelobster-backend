package com.mjsamaha.codelobster.rating;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjsamaha.codelobster.common.Rank;
import com.mjsamaha.codelobster.common.exception.UserNotFoundException;
import com.mjsamaha.codelobster.contests.Contest;
import com.mjsamaha.codelobster.user.User;
import com.mjsamaha.codelobster.user.UserRepository;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private final UserRepository userRepository;
    private final RatingHistoryRepository ratingHistoryRepository;
    private final RankService rankService;

    public RatingServiceImpl(
            UserRepository userRepository,
            RatingHistoryRepository ratingHistoryRepository,
            RankService rankService) {
        this.userRepository = userRepository;
        this.ratingHistoryRepository = ratingHistoryRepository;
        this.rankService = rankService;
    }

    @Override
    public RatingHistory applyNewRating(Long userId, int newRating, int globalRank, Contest contest, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        int safeNewRating = Math.max(0, newRating);
        int oldRating = user.getRating() == null ? 0 : user.getRating();
        Rank oldRank = user.getRank() == null ? Rank.UNRANKED : user.getRank();

        user.setRating(safeNewRating);
        Rank newRank = rankService.calculateRank(user, globalRank);
        user.setRank(newRank);
        userRepository.save(user);

        RatingHistory history = new RatingHistory();
        history.setUser(user);
        history.setContest(contest);
        history.setOldRating(oldRating);
        history.setNewRating(safeNewRating);
        history.setRatingChange(safeNewRating - oldRating);
        history.setOldRank(oldRank);
        history.setNewRank(newRank);
        history.setChangedAt(LocalDateTime.now());
        history.setReason(reason);

        return ratingHistoryRepository.save(history);
    }

    @Override
    public RatingHistory applyRatingDelta(Long userId, int ratingDelta, int globalRank, Contest contest, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        int currentRating = user.getRating() == null ? 0 : user.getRating();
        return applyNewRating(userId, currentRating + ratingDelta, globalRank, contest, reason);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingHistory> getRecentHistory(Long userId) {
        return ratingHistoryRepository.findTop50ByUserIdOrderByChangedAtDesc(userId);
    }
}
