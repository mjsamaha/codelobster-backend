package com.mjsamaha.codelobster.module;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mjsamaha.codelobster.problem.ProblemCategory;
import com.mjsamaha.codelobster.problem.ProblemDifficulty;
import com.mjsamaha.codelobster.problem.Tag;
import com.mjsamaha.codelobster.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "modules")
public class Module {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    private ProblemCategory category; // Optional: module focus area
    
    @ManyToMany
    @JoinTable(
            name = "module_tag_links",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
    
    @Enumerated(EnumType.STRING)
    private ProblemDifficulty difficultyLevel;
    
    @Column(nullable = false)
    private Integer orderIndex = 0; // Module sequence in curriculum
    
    @Column(nullable = false)
    private Boolean isPublished = false;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime publishedAt;
    
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    
    private String imageUrl; // Module cover image
    
    private Integer estimatedHours; // Time to complete
    
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @OrderBy("orderIndex ASC")
    private List<ModuleProblem> problems;
    
    @OneToMany(mappedBy = "module")
    private List<ModuleProgress> userProgress;

}
