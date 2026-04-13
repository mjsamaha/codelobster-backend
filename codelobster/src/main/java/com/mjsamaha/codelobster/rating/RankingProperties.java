package com.mjsamaha.codelobster.rating;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.ranking")
public class RankingProperties {

    private int minSolvedForRank = 5;
    private int minContestsForRank = 1;
    private int defaultGlobalRank = Integer.MAX_VALUE;

    public int getMinSolvedForRank() {
        return minSolvedForRank;
    }

    public void setMinSolvedForRank(int minSolvedForRank) {
        this.minSolvedForRank = minSolvedForRank;
    }

    public int getMinContestsForRank() {
        return minContestsForRank;
    }

    public void setMinContestsForRank(int minContestsForRank) {
        this.minContestsForRank = minContestsForRank;
    }

    public int getDefaultGlobalRank() {
        return defaultGlobalRank;
    }

    public void setDefaultGlobalRank(int defaultGlobalRank) {
        this.defaultGlobalRank = defaultGlobalRank;
    }
}
