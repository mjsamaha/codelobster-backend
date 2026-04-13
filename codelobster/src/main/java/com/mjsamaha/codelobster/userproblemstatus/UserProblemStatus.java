package com.mjsamaha.codelobster.userproblemstatus;

import java.time.LocalDateTime;

import com.mjsamaha.codelobster.problem.Problem;
import com.mjsamaha.codelobster.problem.ProblemStatus;
import com.mjsamaha.codelobster.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user_problem_status",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "problem_id"}))
public class UserProblemStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemStatus status; // UNSOLVED, ATTEMPTED, SOLVED
    
    @Column(nullable = false)
    private Integer attemptCount = 0;
    
    private LocalDateTime firstAttemptAt;
    
    private LocalDateTime solvedAt;
    
    @Column(nullable = false)
    private Integer bestScore = 0; // If partial scoring
}