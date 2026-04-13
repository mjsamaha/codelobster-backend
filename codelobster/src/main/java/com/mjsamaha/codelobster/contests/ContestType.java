package com.mjsamaha.codelobster.contests;

public enum ContestType {
	
	/**
     * Rated contest - affects user rating and ranking
     * Results contribute to overall user rating calculation
     */
    RATED,
    
    /**
     * Unrated contest - for practice or fun
     * No rating changes, but still counts toward "contests participated"
     * requirement for rank eligibility
     */
    UNRATED,
    
    /**
     * Practice contest - old contests available for practice
     * Can be taken anytime, multiple attempts allowed
     * Does NOT count toward "contests participated" requirement
     */
    PRACTICE,
    
    /**
     * Educational contest - focused on learning
     * May have extended time, hints, or other educational features
     * Counts toward participation but not rated
     */
    EDUCATIONAL,
    
    /**
     * Virtual contest - user-initiated simulation of past contests
     * User can "start" an old contest and compete in real-time simulation
     * Not rated, but provides contest experience
     */
    VIRTUAL

}
