package com.mjsamaha.codelobster.rating;

import java.time.LocalDateTime;

import com.mjsamaha.codelobster.common.Rank;
import com.mjsamaha.codelobster.contests.Contest;
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
@Table(name = "rating_history", indexes = {
    @Index(name = "idx_user_date", columnList = "user_id,changedAt")
})
public class RatingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;
    
    @Column(nullable = false)
    private Integer oldRating;
    
    @Column(nullable = false)
    private Integer newRating;
    
    @Column(nullable = false)
    private Integer ratingChange;
    
    @Enumerated(EnumType.STRING)
    private Rank oldRank;
    
    @Enumerated(EnumType.STRING)
    private Rank newRank;
    
    @Column(nullable = false)
    private LocalDateTime changedAt;
    
    private String reason; // "Contest #123", "Manual adjustment"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Integer getOldRating() {
        return oldRating;
    }

    public void setOldRating(Integer oldRating) {
        this.oldRating = oldRating;
    }

    public Integer getNewRating() {
        return newRating;
    }

    public void setNewRating(Integer newRating) {
        this.newRating = newRating;
    }

    public Integer getRatingChange() {
        return ratingChange;
    }

    public void setRatingChange(Integer ratingChange) {
        this.ratingChange = ratingChange;
    }

    public Rank getOldRank() {
        return oldRank;
    }

    public void setOldRank(Rank oldRank) {
        this.oldRank = oldRank;
    }

    public Rank getNewRank() {
        return newRank;
    }

    public void setNewRank(Rank newRank) {
        this.newRank = newRank;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
