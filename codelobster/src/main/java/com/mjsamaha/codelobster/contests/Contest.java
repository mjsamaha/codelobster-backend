package com.mjsamaha.codelobster.contests;

import java.time.LocalDateTime;
import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "contests")
public class Contest {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
    @Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ContestType type; // UNRATED, UNRATED, PRACTICE
	
	@Column(nullable = false)
	private LocalDateTime startTime;
	
	@Column(nullable = false)
	private LocalDateTime endTime;
	
	@Column(nullable = false)
	private Integer durationMinutes; // Duration in minutes for practice contests
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ContestStatus status; // UPCOMING, RUNNNG, FINISHED
	
	@Column(nullable = false)
	private Integer maxParticipants = -1; // -1 for unlimited
	
	@Column(nullable = false)
	private Integer minRating = 0; // Division restrictions
	
	@Column(nullable = false)
	private Integer maxRating = 4000; // Division restrictions
	
	@Column(nullable = false)
	private Boolean isPublic = true; // Public vs private contest
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author; // Contest creator (optional)
	
	@Column(columnDefinition = "TEXT")
	private String rules; // Custom rules or announcements for the contest
	
	@OneToMany(mappedBy = "contest")
    private List<ContestProblem> problems;
    
    @OneToMany(mappedBy = "contest")
    private List<ContestParticipation> participants;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
