package com.mjsamaha.codelobster.submission;

public enum SubmissionStatus {
	
	PENDING,          // In queue
    RUNNING,          // Currently being judged
    ACCEPTED,         // AC
    WRONG_ANSWER,     // WA
    TIME_LIMIT_EXCEEDED,  // TLE
    MEMORY_LIMIT_EXCEEDED, // MLE
    RUNTIME_ERROR,    // RE
    COMPILE_ERROR,    // CE
    SYSTEM_ERROR      // Judge error

}
