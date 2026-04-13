package com.mjsamaha.codelobster.rating;

import com.mjsamaha.codelobster.common.Rank;
import com.mjsamaha.codelobster.user.User;

public interface RankService {

    boolean isEligibleForRank(User user);

    Rank calculateRank(User user);

    Rank calculateRank(User user, int globalRank);

    Rank calculateRank(int rating, int problemSolved, int contestParticipated, int globalRank);
}
