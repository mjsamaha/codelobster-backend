package com.mjsamaha.codelobster.problem;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mjsamaha.codelobster.problem.dto.CreateProblemRequest;
import com.mjsamaha.codelobster.problem.dto.ProblemAdminDetailResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemCategoryRequest;
import com.mjsamaha.codelobster.problem.dto.ProblemDetailResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemStatsResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemSummaryResponse;
import com.mjsamaha.codelobster.problem.dto.TagResponse;
import com.mjsamaha.codelobster.problem.dto.UpdateProblemRequest;
import com.mjsamaha.codelobster.user.User;

@Component
public class ProblemMapper {
	
	// Entity -> Response Projections
	
	public ProblemSummaryResponse toSummaryResponse(Problem p) {
        return new ProblemSummaryResponse(
                p.getId(),
                p.getTitle(),
                p.getCategory().name(),
                mapTags(p.getTags()),
                p.getRating(),
                p.getDifficulty().getDisplayName(),
                p.getDifficulty().getColorHex(),
                p.getSolveCount(),
                p.getSubmissionCount(),
                computeAcceptanceRate(p),
                p.getCreatedAt()
        );
    }

    public ProblemDetailResponse toDetailResponse(Problem p) {
        return new ProblemDetailResponse(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getCategory().name(),
                mapTags(p.getTags()),
                p.getRating(),
                p.getDifficulty().getDisplayName(),
                p.getDifficulty().getColorHex(),
                p.getTimeLimit(),
                p.getMemoryLimit(),
                p.getInputFormat(),
                p.getOutputFormat(),
                p.getConstraints(),
                p.getSampleInput(),
                p.getSampleOutput(),
                p.getSampleExplanation(),
                toStatsResponse(p),
                p.getAuthor() != null ? p.getAuthor().getUsername() : null,
                p.getCreatedAt()
        );
    }

    public ProblemAdminDetailResponse toAdminDetailResponse(Problem p) {
        return new ProblemAdminDetailResponse(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getCategory().name(),
                mapTags(p.getTags()),
                p.getRating(),
                p.getDifficulty().getDisplayName(),
                p.getDifficulty().getColorHex(),
                p.getTimeLimit(),
                p.getMemoryLimit(),
                p.getIsPublished(),
                p.getInputFormat(),
                p.getOutputFormat(),
                p.getConstraints(),
                p.getSampleInput(),
                p.getSampleOutput(),
                p.getSampleExplanation(),
                toStatsResponse(p),
                p.getAuthor() != null ? p.getAuthor().getId() : null,
                p.getAuthor() != null ? p.getAuthor().getUsername() : null,
                p.getCreatedAt()
        );
    }

    public ProblemStatsResponse toStatsResponse(Problem p) {
        return new ProblemStatsResponse(
                p.getSolveCount(),
                p.getSubmissionCount(),
                p.getAttemptCount(),
                computeAcceptanceRate(p)
        );
    }
    
    
    // Request -> Entity
    /**
     * Builds a new Problem entity from a create request.
     * Difficulty is NOT set here — the service derives it from rating after this call.
     */
    public Problem toEntity(CreateProblemRequest req, User author, Set<Tag> tags) {
        Problem p = new Problem();
        p.setTitle(req.title());
        p.setDescription(req.description());
        p.setCategory(mapCategory(req.category()));
        p.setTags(tags);
        p.setRating(req.rating());
        p.setTimeLimit(req.timeLimit());
        p.setMemoryLimit(req.memoryLimit());
        p.setInputFormat(req.inputFormat());
        p.setOutputFormat(req.outputFormat());
        p.setConstraints(req.constraints());
        p.setSampleInput(req.sampleInput());
        p.setSampleOutput(req.sampleOutput());
        p.setSampleExplanation(req.sampleExplanation());
        p.setAuthor(author);
        p.setIsPublished(false); // always starts as a draft
        return p;
    }

    /**
     * Applies only the non-null fields from the request onto the existing entity.
     * Tag replacement and difficulty re-derivation are handled by the service.
     */
    public void updateEntityFromRequest(UpdateProblemRequest req, Problem p) {
        if (req.title() != null)             p.setTitle(req.title());
        if (req.description() != null)       p.setDescription(req.description());
        if (req.category() != null)          p.setCategory(mapCategory(req.category()));
        if (req.rating() != null)            p.setRating(req.rating());
        if (req.timeLimit() != null)         p.setTimeLimit(req.timeLimit());
        if (req.memoryLimit() != null)       p.setMemoryLimit(req.memoryLimit());
        if (req.inputFormat() != null)       p.setInputFormat(req.inputFormat());
        if (req.outputFormat() != null)      p.setOutputFormat(req.outputFormat());
        if (req.constraints() != null)       p.setConstraints(req.constraints());
        if (req.sampleInput() != null)       p.setSampleInput(req.sampleInput());
        if (req.sampleOutput() != null)      p.setSampleOutput(req.sampleOutput());
        if (req.sampleExplanation() != null) p.setSampleExplanation(req.sampleExplanation());
    }
    
    private Set<TagResponse> mapTags(Set<Tag> tags) {
        if (tags == null) return Set.of();
        return tags.stream()
                .map(t -> new TagResponse(t.getId(), t.getName(), t.getType().name()))
                .collect(Collectors.toSet());
    }

    private ProblemCategory mapCategory(ProblemCategoryRequest categoryRequest) {
        return ProblemCategory.valueOf(categoryRequest.name());
    }

    private double computeAcceptanceRate(Problem p) {
        if (p.getSubmissionCount() == null || p.getSubmissionCount() == 0) return 0.0;
        return (double) p.getSolveCount() / p.getSubmissionCount() * 100.0;
    }

}
