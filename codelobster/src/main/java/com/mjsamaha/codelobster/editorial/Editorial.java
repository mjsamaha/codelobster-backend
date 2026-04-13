package com.mjsamaha.codelobster.editorial;

import java.time.LocalDateTime;

import com.mjsamaha.codelobster.common.ProgrammingLanguage;
import com.mjsamaha.codelobster.problem.Problem;
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

@Entity
@Table(name = "editorials")
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String markdownContent;
    
    @Column(nullable = false)
    private Boolean isOfficial = false;
    
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @Column(nullable = false)
    private Integer upvotes = 0;
    
    @Column(nullable = false)
    private Integer downvotes = 0;
    
    @Column(nullable = false)
    private Boolean isPublished = false;
    
    @Enumerated(EnumType.STRING)
    private ProgrammingLanguage primaryLanguage; // Main language used in code examples
}