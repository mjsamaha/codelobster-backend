package com.mjsamaha.codelobster.testcase;

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
@Table(name = "test_cases")
public class TestCase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String input;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String expectedOutput;
    
    @Column(nullable = false)
    private Boolean isVisible = false; // Sample vs hidden test cases
    
    @Column(nullable = false)
    private Boolean isActive = true; // Can disable without deleting
    
    @Column(nullable = false)
    private Integer orderIndex = 0; // Execution order
    
    @Column(nullable = false)
    private Integer points = 1; // Weighted scoring (optional)
    
    private String description; // What this test case validates

}
