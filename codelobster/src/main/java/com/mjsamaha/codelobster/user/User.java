package com.mjsamaha.codelobster.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.mjsamaha.codelobster.common.Country;
import com.mjsamaha.codelobster.common.Rank;
import com.mjsamaha.codelobster.contests.ContestParticipation;
import com.mjsamaha.codelobster.editorial.Editorial;
import com.mjsamaha.codelobster.module.ModuleProgress;
import com.mjsamaha.codelobster.submission.Submission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username; // unique constraint should be applied at the database level

	@Column(unique = true, nullable = false)
	private String email; // unique constraint should be applied at the database level

	@Column(nullable = false)
	private String password; // Bcrypt Hash

	/**
	 * Single role per user - highest privilege level they have Roles are
	 * hierarchical: higher roles have all lower role permissions
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role = UserRole.USER; // default role for new users

	private Integer rating = 0; // starting rating

	@Enumerated(EnumType.STRING)
	private Rank rank = Rank.UNRANKED; // starting rank

	@Column(nullable = false)
	private Integer problemSolved = 0;

	@Column(nullable = false)
	private Integer contestParticipated = 0;

	@Column(nullable = false)
	private Boolean enabled = true; // for account activation/deactivation

	@Column(nullable = false)
	private Boolean emailVerified = false; // for email verification

	private String verificationToken; // for email verification

	@Column(updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	private LocalDateTime lastLoginAt; // for tracking last login time

	@Enumerated(EnumType.STRING)
	private Country country; // for user profile and localization

	@Column(length = 1000)
	private String bio; // for user profile

	// One-to-Many Relationships
	@OneToMany(mappedBy = "user")
	private List<Submission> submissions;

	@OneToMany(mappedBy = "user")
	private List<ContestParticipation> contestParticipations;

	@OneToMany(mappedBy = "user")
	private List<ModuleProgress> moduleProgresses;

	@OneToMany(mappedBy = "user")
	private List<Editorial> editorials;

	public User() {

	}

	public User(String username, String email, String password, UserRole role, Integer rating, Rank rank,
			Integer problemSolved, Integer contestParticipated, Boolean enabled, Boolean emailVerified,
			String verificationToken, LocalDateTime createdAt, LocalDateTime lastLoginAt, Country country, String bio,
			List<Submission> submissions, List<ContestParticipation> contestParticipations,
			List<ModuleProgress> moduleProgresses, List<Editorial> editorials) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.rating = rating;
		this.rank = rank;
		this.problemSolved = problemSolved;
		this.contestParticipated = contestParticipated;
		this.enabled = enabled;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.createdAt = createdAt;
		this.lastLoginAt = lastLoginAt;
		this.country = country;
		this.bio = bio;
		this.submissions = submissions;
		this.contestParticipations = contestParticipations;
		this.moduleProgresses = moduleProgresses;
		this.editorials = editorials;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Integer getProblemSolved() {
		return problemSolved;
	}

	public void setProblemSolved(Integer problemSolved) {
		this.problemSolved = problemSolved;
	}

	public Integer getContestParticipated() {
		return contestParticipated;
	}

	public void setContestParticipated(Integer contestParticipated) {
		this.contestParticipated = contestParticipated;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(LocalDateTime lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<Submission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<Submission> submissions) {
		this.submissions = submissions;
	}

	public List<ContestParticipation> getContestParticipations() {
		return contestParticipations;
	}

	public void setContestParticipations(List<ContestParticipation> contestParticipations) {
		this.contestParticipations = contestParticipations;
	}

	public List<ModuleProgress> getModuleProgresses() {
		return moduleProgresses;
	}

	public void setModuleProgresses(List<ModuleProgress> moduleProgresses) {
		this.moduleProgresses = moduleProgresses;
	}

	public List<Editorial> getEditorials() {
		return editorials;
	}

	public void setEditorials(List<Editorial> editorials) {
		this.editorials = editorials;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bio, contestParticipated, contestParticipations, country, createdAt, editorials, email,
				emailVerified, enabled, id, lastLoginAt, moduleProgresses, password, problemSolved, rank, rating, role,
				submissions, username, verificationToken);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(bio, other.bio) && Objects.equals(contestParticipated, other.contestParticipated)
				&& Objects.equals(contestParticipations, other.contestParticipations) && country == other.country
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(editorials, other.editorials)
				&& Objects.equals(email, other.email) && Objects.equals(emailVerified, other.emailVerified)
				&& Objects.equals(enabled, other.enabled) && Objects.equals(id, other.id)
				&& Objects.equals(lastLoginAt, other.lastLoginAt)
				&& Objects.equals(moduleProgresses, other.moduleProgresses) && Objects.equals(password, other.password)
				&& Objects.equals(problemSolved, other.problemSolved) && rank == other.rank
				&& Objects.equals(rating, other.rating) && role == other.role
				&& Objects.equals(submissions, other.submissions) && Objects.equals(username, other.username)
				&& Objects.equals(verificationToken, other.verificationToken);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", role="
				+ role + ", rating=" + rating + ", rank=" + rank + ", problemSolved=" + problemSolved
				+ ", contestParticipated=" + contestParticipated + ", enabled=" + enabled + ", emailVerified="
				+ emailVerified + ", verificationToken=" + verificationToken + ", createdAt=" + createdAt
				+ ", lastLoginAt=" + lastLoginAt + ", country=" + country + ", bio=" + bio + ", submissions="
				+ submissions + ", contestParticipations=" + contestParticipations + ", moduleProgresses="
				+ moduleProgresses + ", editorials=" + editorials + "]";
	}
}
