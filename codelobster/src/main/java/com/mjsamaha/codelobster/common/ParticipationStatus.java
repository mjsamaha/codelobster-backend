package com.mjsamaha.codelobster.common;

public enum ParticipationStatus {
	
	/**
     * User has registered but hasn't started yet
     * For contests with virtual start times, user hasn't clicked "Start"
     */
    REGISTERED,
    
    /**
     * User has opened the contest and is actively participating
     * At least one problem has been viewed
     */
    PARTICIPATING,
    
    /**
     * User has completed participation (time ended or manually finished)
     * Final score is recorded
     */
    FINISHED,
    
    /**
     * User registered but never started
     * Happens when registration closes but user didn't participate
     */
    NO_SHOW,
    
    /**
     * User was disqualified due to violation of rules
     * Cheating, plagiarism, or other policy violations
     */
    DISQUALIFIED,
    
    /**
     * User withdrew from the contest voluntarily
     * May happen during registration or active participation
     */
    WITHDRAWN

}
