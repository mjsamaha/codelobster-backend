package com.mjsamaha.codelobster.problem;

public enum ProblemStatus {
	
	/**
     * User has never attempted this problem
     * No submissions exist
     */
    UNSOLVED,
    
    /**
     * User has made at least one submission but hasn't solved it
     * May have WA, TLE, RE, CE, or other non-AC verdicts
     */
    ATTEMPTED,
    
    /**
     * User has at least one accepted (AC) submission
     * Problem is considered solved
     */
    SOLVED,
    
    /**
     * User solved the problem during a contest
     * Special badge/indicator for contest solves
     */
    SOLVED_IN_CONTEST,
    
    /**
     * User solved after looking at editorial
     * Optional: helps track independent vs assisted solves
     */
    SOLVED_WITH_EDITORIAL,
    
    /**
     * User has solved with optimal solution
     * Could be based on time/memory benchmarks or problem setter criteria
     */
    OPTIMALLY_SOLVED

}
