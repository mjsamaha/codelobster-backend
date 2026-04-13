package com.mjsamaha.codelobster.module;

import java.time.LocalDateTime;

import com.mjsamaha.codelobster.common.ProgressStatus;
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
@Table(name = "module_progress", 
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "module_id"}))
public class ModuleProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;
    
    @Column(nullable = false)
    private Integer problemsCompleted = 0;
    
    @Column(nullable = false)
    private Integer totalProblems;
    
    @Column(nullable = false)
    private Integer progressPercentage = 0;
    
    @Column(nullable = false)
    private LocalDateTime startedAt;
    
    private LocalDateTime completedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgressStatus status; // NOT_STARTED, IN_PROGRESS, COMPLETED
}