package com.mjsamaha.codelobster.common;

public enum ProgressStatus {
	
	/**
     * User hasn't started this module yet
     */
    NOT_STARTED,
    
    /**
     * User has solved at least one problem in the module
     */
    IN_PROGRESS,
    
    /**
     * User has completed all required problems in the module
     */
    COMPLETED,
    
    /**
     * User has completed all problems (required + optional)
     */
    FULLY_COMPLETED

}
