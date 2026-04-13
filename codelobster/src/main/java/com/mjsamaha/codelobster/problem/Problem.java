package com.mjsamaha.codelobster.problem;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.mjsamaha.codelobster.editorial.Editorial;
import com.mjsamaha.codelobster.module.ModuleProblem;
import com.mjsamaha.codelobster.submission.Submission;
import com.mjsamaha.codelobster.testcase.TestCase;
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
import jakarta.persistence.Table;

@Entity
@Table(name = "problems")
public class Problem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 100)
    private String title;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description; // Markdown/HTML
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemCategory category;
    
    @ManyToMany
    @JoinTable(
            name = "problem_tag_links",
            joinColumns = @JoinColumn(name = "problem_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
    
    @Column(nullable = false)
    private Integer rating; // Difficulty rating (400-3900)
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemDifficulty difficulty; // EASY, MEDIUM, HARD
	
    @Column(nullable = false)
	private Integer timeLimit; // in milliseconds
	
    @Column(nullable = false)
	private Integer memoryLimit; // in megabytes
	
    @Column(nullable = false)
	private Boolean isPublished = false; // for problem visibility control
	
    @Column(nullable = false)
	private Integer solveCount = 0; // for tracking how many users have solved the problem
	
    @Column(nullable = false)
	private Integer submissionCount = 0; // for tracking total submissions to the problem
	
    @Column(nullable = false)
	private Integer attemptCount = 0; // for tracking total attempts on the problem
	
	private LocalDateTime createdAt = LocalDateTime.now(); // for tracking when the problem was created
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author; // for tracking who created the problem
	
	// Constraints section (input/output format)
    @Column(columnDefinition = "TEXT")
    private String inputFormat;
    
    @Column(columnDefinition = "TEXT")
    private String outputFormat;
    
    @Column(columnDefinition = "TEXT")
    private String constraints;
    
    // Sample examples (not test cases)
    @Column(columnDefinition = "TEXT")
    private String sampleInput;
    
    @Column(columnDefinition = "TEXT")
    private String sampleOutput;
    
    @Column(columnDefinition = "TEXT")
    private String sampleExplanation;
    
    // Relationships
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<TestCase> testCases;
    
    @OneToMany(mappedBy = "problem")
    private List<Submission> submissions;
    
    @OneToMany(mappedBy = "problem")
    private List<Editorial> editorials;
    
    @OneToMany(mappedBy = "problem")
    private List<ModuleProblem> moduleProblems;
    
    public Problem() {
	}
    	
	public Problem(String title, String description, ProblemCategory category, Set<Tag> tags, Integer rating,
			ProblemDifficulty difficulty, Integer timeLimit, Integer memoryLimit, Boolean isPublished,
			Integer solveCount, Integer submissionCount, Integer attemptCount, LocalDateTime createdAt, User author,
			String inputFormat, String outputFormat, String constraints, String sampleInput, String sampleOutput,
			String sampleExplanation, List<TestCase> testCases, List<Submission> submissions,
			List<Editorial> editorials, List<ModuleProblem> moduleProblems) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.tags = tags;
		this.rating = rating;
		this.difficulty = difficulty;
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
		this.isPublished = isPublished;
		this.solveCount = solveCount;
		this.submissionCount = submissionCount;
		this.attemptCount = attemptCount;
		this.createdAt = createdAt;
		this.author = author;
		this.inputFormat = inputFormat;
		this.outputFormat = outputFormat;
		this.constraints = constraints;
		this.sampleInput = sampleInput;
		this.sampleOutput = sampleOutput;
		this.sampleExplanation = sampleExplanation;
		this.testCases = testCases;
		this.submissions = submissions;
		this.editorials = editorials;
		this.moduleProblems = moduleProblems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProblemCategory getCategory() {
		return category;
	}

	public void setCategory(ProblemCategory category) {
		this.category = category;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public ProblemDifficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(ProblemDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(Integer memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Integer getSolveCount() {
		return solveCount;
	}

	public void setSolveCount(Integer solveCount) {
		this.solveCount = solveCount;
	}

	public Integer getSubmissionCount() {
		return submissionCount;
	}

	public void setSubmissionCount(Integer submissionCount) {
		this.submissionCount = submissionCount;
	}

	public Integer getAttemptCount() {
		return attemptCount;
	}

	public void setAttemptCount(Integer attemptCount) {
		this.attemptCount = attemptCount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getInputFormat() {
		return inputFormat;
	}

	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	public String getSampleInput() {
		return sampleInput;
	}

	public void setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
	}

	public String getSampleOutput() {
		return sampleOutput;
	}

	public void setSampleOutput(String sampleOutput) {
		this.sampleOutput = sampleOutput;
	}

	public String getSampleExplanation() {
		return sampleExplanation;
	}

	public void setSampleExplanation(String sampleExplanation) {
		this.sampleExplanation = sampleExplanation;
	}

	public List<TestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}

	public List<Submission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<Submission> submissions) {
		this.submissions = submissions;
	}

	public List<Editorial> getEditorials() {
		return editorials;
	}

	public void setEditorials(List<Editorial> editorials) {
		this.editorials = editorials;
	}

	public List<ModuleProblem> getModuleProblems() {
		return moduleProblems;
	}

	public void setModuleProblems(List<ModuleProblem> moduleProblems) {
		this.moduleProblems = moduleProblems;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attemptCount, author, category, constraints, createdAt, description, difficulty, editorials,
				id, inputFormat, isPublished, memoryLimit, moduleProblems, outputFormat, rating, sampleExplanation,
				sampleInput, sampleOutput, solveCount, submissionCount, submissions, tags, testCases, timeLimit, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Problem other = (Problem) obj;
		return Objects.equals(attemptCount, other.attemptCount) && Objects.equals(author, other.author)
				&& category == other.category && Objects.equals(constraints, other.constraints)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(description, other.description)
				&& difficulty == other.difficulty && Objects.equals(editorials, other.editorials)
				&& Objects.equals(id, other.id) && Objects.equals(inputFormat, other.inputFormat)
				&& Objects.equals(isPublished, other.isPublished) && Objects.equals(memoryLimit, other.memoryLimit)
				&& Objects.equals(moduleProblems, other.moduleProblems)
				&& Objects.equals(outputFormat, other.outputFormat) && Objects.equals(rating, other.rating)
				&& Objects.equals(sampleExplanation, other.sampleExplanation)
				&& Objects.equals(sampleInput, other.sampleInput) && Objects.equals(sampleOutput, other.sampleOutput)
				&& Objects.equals(solveCount, other.solveCount)
				&& Objects.equals(submissionCount, other.submissionCount)
				&& Objects.equals(submissions, other.submissions) && Objects.equals(tags, other.tags)
				&& Objects.equals(testCases, other.testCases) && Objects.equals(timeLimit, other.timeLimit)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Problem [id=" + id + ", title=" + title + ", description=" + description + ", category=" + category
				+ ", tags=" + tags + ", rating=" + rating + ", difficulty=" + difficulty + ", timeLimit=" + timeLimit
				+ ", memoryLimit=" + memoryLimit + ", isPublished=" + isPublished + ", solveCount=" + solveCount
				+ ", submissionCount=" + submissionCount + ", attemptCount=" + attemptCount + ", createdAt=" + createdAt
				+ ", author=" + author + ", inputFormat=" + inputFormat + ", outputFormat=" + outputFormat
				+ ", constraints=" + constraints + ", sampleInput=" + sampleInput + ", sampleOutput=" + sampleOutput
				+ ", sampleExplanation=" + sampleExplanation + ", testCases=" + testCases + ", submissions="
				+ submissions + ", editorials=" + editorials + ", moduleProblems=" + moduleProblems + "]";
	}

}
