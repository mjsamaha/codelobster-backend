package com.mjsamaha.codelobster.rating;

import java.util.List;

import com.mjsamaha.codelobster.contests.Contest;

public interface RatingService {

    RatingHistory applyNewRating(Long userId, int newRating, int globalRank, Contest contest, String reason);

    RatingHistory applyRatingDelta(Long userId, int ratingDelta, int globalRank, Contest contest, String reason);

    List<RatingHistory> getRecentHistory(Long userId);
}
