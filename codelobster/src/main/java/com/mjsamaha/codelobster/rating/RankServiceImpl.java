package com.mjsamaha.codelobster.rating;

import org.springframework.stereotype.Service;

import com.mjsamaha.codelobster.common.Rank;
import com.mjsamaha.codelobster.user.User;

@Service
public class RankServiceImpl implements RankService {

    private final RankingProperties rankingProperties;

    public RankServiceImpl(RankingProperties rankingProperties) {
        this.rankingProperties = rankingProperties;
    }

    @Override
    public boolean isEligibleForRank(User user) {
        if (user == null) {
            return false;
        }
        int solved = user.getProblemSolved() == null ? 0 : user.getProblemSolved();
        int contests = user.getContestParticipated() == null ? 0 : user.getContestParticipated();
        return solved >= rankingProperties.getMinSolvedForRank()
                && contests >= rankingProperties.getMinContestsForRank();
    }

    @Override
    public Rank calculateRank(User user) {
        return calculateRank(user, rankingProperties.getDefaultGlobalRank());
    }

    @Override
    public Rank calculateRank(User user, int globalRank) {
        if (user == null) {
            return Rank.UNRANKED;
        }
        int rating = user.getRating() == null ? 0 : user.getRating();
        int solved = user.getProblemSolved() == null ? 0 : user.getProblemSolved();
        int contests = user.getContestParticipated() == null ? 0 : user.getContestParticipated();
        return calculateRank(rating, solved, contests, globalRank);
    }

    @Override
    public Rank calculateRank(int rating, int problemSolved, int contestParticipated, int globalRank) {
        if (problemSolved < rankingProperties.getMinSolvedForRank()
                || contestParticipated < rankingProperties.getMinContestsForRank()) {
            return Rank.UNRANKED;
        }
        int safeRating = Math.max(0, rating);
        int safeGlobalRank = Math.max(1, globalRank);
        return Rank.calculateRank(safeRating, safeGlobalRank);
    }
}
