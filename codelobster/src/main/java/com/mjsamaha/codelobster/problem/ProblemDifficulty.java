package com.mjsamaha.codelobster.problem;

public enum ProblemDifficulty {
	
	EASY("Easy", 1, "#22c55e"),           // Green
    MEDIUM("Medium", 2, "#f59e0b"),       // Orange
    HARD("Hard", 3, "#ef4444");          // Red
    
    private final String displayName;
    private final int level;
    private final String colorHex;
    
    ProblemDifficulty(String displayName, int level, String colorHex) {
        this.displayName = displayName;
        this.level = level;
        this.colorHex = colorHex;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getLevel() {
        return level;
    }
    
    public String getColorHex() {
        return colorHex;
    }
    
    /**
     * Get difficulty based on problem rating
     */
    public static ProblemDifficulty fromRating(int rating) {
        if (rating < 1000) return EASY;
        if (rating < 1600) return MEDIUM;
        if (rating < 2200) return HARD;
		return HARD; // Default to HARD for very high ratings}
    }
}
