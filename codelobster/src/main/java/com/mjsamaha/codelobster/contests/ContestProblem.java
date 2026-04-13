package com.mjsamaha.codelobster.contests;

import com.mjsamaha.codelobster.problem.Problem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contest_problems")
public class ContestProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;
    
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Column(nullable = false)
    private Integer orderIndex; // A, B, C, D...
    
    @Column(nullable = false)
    private Integer points; // Contest-specific scoring
    
    private Integer solveCount = 0; // During contest
    
    private Integer attemptCount = 0;
}