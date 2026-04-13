package com.mjsamaha.codelobster.contests;

import java.time.LocalDateTime;

import com.mjsamaha.codelobster.common.ParticipationStatus;
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
@Table(name = "contest_participations")
public class ContestParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;
    
    @Column(nullable = false)
    private LocalDateTime registeredAt;
    
    private LocalDateTime startedAt; // When user opened contest
    
    @Column(nullable = false)
    private Integer totalScore = 0;
    
    @Column(nullable = false)
    private Integer problemsSolved = 0;
    
    private Integer rank; // Final ranking
    
    private Integer ratingBefore;
    
    private Integer ratingAfter;
    
    private Integer ratingChange;
    
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status; // REGISTERED, PARTICIPATING, FINISHED, DISQUALIFIED
}