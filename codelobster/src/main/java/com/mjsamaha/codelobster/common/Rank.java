package com.mjsamaha.codelobster.common;

public enum Rank {
	
	// Low Ranks (Beginner progression: learning fundamentals)
	
    UNRANKED("Unranked", 0, 399),          // New users / no meaningful activity
    INITIATE("Initiate", 400, 699),        // Basic exposure to problems
    NOVICE("Novice", 700, 999),            // Understanding simple patterns
    DISCIPLE("Disciple", 1000, 1299),      // Consistent problem solving
    
    // Mid Ranks (Developing problem-solving ability)
    
    PRACTITIONER("Practitioner", 1300, 1599), // Comfortable with common patterns
    ACOLYTE("Acolyte", 1600, 1899),           // Expanding algorithm knowledge
    MENTOR("Mentor", 1900, 2199),             // Strong fundamentals, fewer mistakes
    PRECEPTOR("Preceptor", 2200, 2499),       // Advanced problem solving consistency
    
    // Advanced Ranks (High skill, near top-level users)
    
    SEER("Seer", 2500, 2799),             // Top ~5–10% of users
    ORACLE("Oracle", 2800, 3099),         // Top ~1–5% of users
    
    // Elite Ranks (Top competitive users)
    
    KYRIE_I("Kyrie I", 3100, 3299),       // Elite tier entry
    KYRIE_II("Kyrie II", 3300, 3499),     // Highly optimized problem solving
    KYRIE_III("Kyrie III", 3500, 3699),   // Among the best performers
    EMPYREAN("Empyrean", 3700, 3899),     // Top ~100 global players
    
    // Prestige Ranks (Leaderboard-based overrides)
    
    ASCENDED("Ascended", 3900, Integer.MAX_VALUE), // Top 11–25 globally (requires 3900+ rating)
    ETERNAL("Eternal", 3900, Integer.MAX_VALUE);   // Top 1–10 globally (requires 3900+ rating)
    
    private final String displayName;
    private final int minRating;
    private final int maxRating;
    
    Rank(String displayName, int minRating, int maxRating) {
        this.displayName = displayName;
        this.minRating = minRating;
        this.maxRating = maxRating;
    }
    
    public String getDisplayName() {
        return displayName;
    }

    public int getMinRating() {
        return minRating;
    }

    public int getMaxRating() {
        return maxRating;
    }
    
    public static Rank calculateRank(int rating, int globalRank) {
        // Special handling for prestige ranks
        if (rating >= 3900) {
            if (globalRank <= 10) return ETERNAL;
            if (globalRank <= 25) return ASCENDED;
        }
        
        // Standard rank calculation
        for (Rank rank : values()) {
            if (rating >= rank.minRating && rating <= rank.maxRating) {
                return rank;
            }
        }
        return UNRANKED;
    }

}
