package com.mjsamaha.codelobster.module;

import java.time.LocalDateTime;

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
@Table(name = "module_problems")
public class ModuleProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;
    
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Column(nullable = false)
    private Integer orderIndex; // Order within module
    
    @Column(nullable = false)
    private Boolean isRequired = true; // Optional vs required problems
    
    private String customHint; // Module-specific hint
    
    @Column(nullable = false)
    private LocalDateTime addedAt;
}