package com.mjsamaha.codelobster.submission;

import java.time.LocalDateTime;

import com.mjsamaha.codelobster.common.ProgrammingLanguage;
import com.mjsamaha.codelobster.contests.Contest;
import com.mjsamaha.codelobster.problem.Problem;
import com.mjsamaha.codelobster.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "submissions", indexes = {
    @Index(name = "idx_user_problem", columnList = "user_id,problem_id"),
    @Index(name = "idx_submitted_at", columnList = "submittedAt")
})
public class Submission {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest; // Null if practice submission
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgrammingLanguage language;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status; // PENDING, RUNNING, ACCEPTED, WRONG_ANSWER, TLE, MLE, RE, CE
    
    @Column(columnDefinition = "JSON")
    private String resultJson; // Detailed results per test case
    
    @Column(nullable = false)
    private LocalDateTime submittedAt;
    
    private LocalDateTime judgedAt;
    
    private Integer executionTime; // in ms
    
    private Integer memoryUsed; // in MB
    
    private Integer testCasesPassed = 0;
    
    private Integer totalTestCases = 0;
    
    private Integer score = 0; // For partial scoring
    
    private String compileError; // If CE
    
    private String runtimeError; // If RE

}
