package com.mjsamaha.codelobster.contests;

public enum ContestStatus {
	
	/**
     * Contest is scheduled but not yet started
     * Users can register during this phase
     */
    UPCOMING,
    
    /**
     * Contest registration is open
     * May close before contest starts or remain open
     */
    REGISTRATION_OPEN,
    
    /**
     * Contest registration has closed
     * Contest may still be upcoming
     */
    REGISTRATION_CLOSED,
    
    /**
     * Contest is currently running
     * Users are actively solving problems
     */
    RUNNING,
    
    /**
     * Contest has ended but ratings not yet calculated
     * Submissions are frozen
     */
    FINISHED,
    
    /**
     * Contest is complete and ratings have been updated
     * Final standings are available
     */
    RATED,
    
    /**
     * Contest was cancelled before or during execution
     */
    CANCELLED

}
