package com.mjsamaha.codelobster.problem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;                         
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;        

import com.mjsamaha.codelobster.common.exception.DuplicateResourceException;
import com.mjsamaha.codelobster.common.exception.ForbiddenException;
import com.mjsamaha.codelobster.common.exception.ResourceNotFoundException;
import com.mjsamaha.codelobster.problem.dto.CreateProblemRequest;
import com.mjsamaha.codelobster.problem.dto.ProblemAdminDetailResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemDetailResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemStatsResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemSummaryResponse;
import com.mjsamaha.codelobster.problem.dto.UpdateProblemRequest;
import com.mjsamaha.codelobster.user.User;
import com.mjsamaha.codelobster.user.UserRepository;
import com.mjsamaha.codelobster.user.UserRole;


@Service
@Transactional
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ProblemMapper problemMapper;

    public ProblemServiceImpl(
            ProblemRepository problemRepository,
            TagRepository tagRepository,
            UserRepository userRepository,
            ProblemMapper problemMapper) {
        this.problemRepository = problemRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.problemMapper = problemMapper;
    }

    // CRUD

    @Override
    public ProblemAdminDetailResponse createProblem(CreateProblemRequest request, Long authorId) {
        if (problemRepository.existsByTitle(request.title())) {
            throw new DuplicateResourceException("Problem", "title", request.title());
        }

        User author = findUserOrThrow(authorId);
        Set<Tag> tags = resolveTags(request.tagIds());

        Problem problem = problemMapper.toEntity(request, author, tags);
        problem.setDifficulty(ProblemDifficulty.fromRating(problem.getRating()));

        return problemMapper.toAdminDetailResponse(problemRepository.save(problem));
    }

    @Override
    public ProblemAdminDetailResponse updateProblem(Long id, UpdateProblemRequest request, Long authorId) {
        Problem problem = findProblemOrThrow(id);
        assertOwnerOrAdmin(problem, authorId);

        if (request.title() != null
                && !request.title().equals(problem.getTitle())
                && problemRepository.existsByTitle(request.title())) {
            throw new DuplicateResourceException("Problem", "title", request.title());
        }

        problemMapper.updateEntityFromRequest(request, problem);

        if (request.rating() != null) {
            problem.setDifficulty(ProblemDifficulty.fromRating(problem.getRating()));
        }
        if (request.tagIds() != null) {
            problem.setTags(resolveTags(request.tagIds()));
        }

        return problemMapper.toAdminDetailResponse(problemRepository.save(problem));
    }

    @Override
    public void deleteProblem(Long id, Long authorId) {
        Problem problem = findProblemOrThrow(id);
        assertOwnerOrAdmin(problem, authorId);
        problemRepository.delete(problem);
    }

    // Publish lifecycle

    @Override
    public ProblemAdminDetailResponse publishProblem(Long id) {
        Problem problem = findProblemOrThrow(id);
        problem.setIsPublished(true);
        return problemMapper.toAdminDetailResponse(problemRepository.save(problem));
    }

    @Override
    public ProblemAdminDetailResponse unpublishProblem(Long id) {
        Problem problem = findProblemOrThrow(id);
        problem.setIsPublished(false);
        return problemMapper.toAdminDetailResponse(problemRepository.save(problem));
    }

    // Public reads

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemSummaryResponse> getPublishedProblems(
            ProblemCategory category, ProblemDifficulty difficulty,
            Set<Long> tagIds, Integer minRating, Integer maxRating,
            String titleKeyword, Pageable pageable) {

        Specification<Problem> spec = ProblemSpecification.publishedOnly()
                .and(buildCommonFilters(category, difficulty, tagIds, minRating, maxRating, titleKeyword));

        return problemRepository.findAll(spec, pageable)
                .map(problemMapper::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemDetailResponse getPublishedProblemById(Long id) {
        Problem problem = findProblemOrThrow(id);
        if (!problem.getIsPublished()) {
            throw new ResourceNotFoundException("Problem", "id", id);
        }
        return problemMapper.toDetailResponse(problem);
    }

    // Admin reads (includes unpublished + more details)

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemSummaryResponse> getAllProblems(
            ProblemCategory category, ProblemDifficulty difficulty,
            Set<Long> tagIds, Integer minRating, Integer maxRating,
            String titleKeyword, Boolean isPublished, Long authorId,
            Pageable pageable) {

        Specification<Problem> spec = Specification.<Problem>where(null);

        if (isPublished != null) spec = spec.and(ProblemSpecification.isPublished(isPublished));
        if (authorId != null)    spec = spec.and(ProblemSpecification.byAuthor(authorId));
        spec = spec.and(buildCommonFilters(category, difficulty, tagIds, minRating, maxRating, titleKeyword));

        return problemRepository.findAll(spec, pageable)
                .map(problemMapper::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemAdminDetailResponse getAdminProblemById(Long id) {
        return problemMapper.toAdminDetailResponse(findProblemOrThrow(id));
    }

    //Tag management

    @Override
    public ProblemAdminDetailResponse addTags(Long problemId, Set<Long> tagIds) {
        Problem problem = findProblemOrThrow(problemId);
        problem.getTags().addAll(resolveTags(tagIds));
        return problemMapper.toAdminDetailResponse(problemRepository.save(problem));
    }

    @Override
    public ProblemAdminDetailResponse removeTags(Long problemId, Set<Long> tagIds) {
        Problem problem = findProblemOrThrow(problemId);
        problem.getTags().removeIf(tag -> tagIds.contains(tag.getId()));
        return problemMapper.toAdminDetailResponse(problemRepository.save(problem));
    }

    // Stats

    @Override
    @Transactional(readOnly = true)
    public ProblemStatsResponse getProblemStats(Long id) {
        return problemMapper.toStatsResponse(findProblemOrThrow(id));
    }

    @Override
    public void incrementSolveCount(Long problemId) {
        ensureProblemExists(problemId);
        problemRepository.incrementSolveCount(problemId);
    }

    @Override
    public void incrementSubmissionCount(Long problemId) {
        ensureProblemExists(problemId);
        problemRepository.incrementSubmissionCount(problemId);
    }

    @Override
    public void incrementAttemptCount(Long problemId) {
        ensureProblemExists(problemId);
        problemRepository.incrementAttemptCount(problemId);
    }

    // Trending 

    @Override
    @Transactional(readOnly = true)
    public List<ProblemSummaryResponse> getTrendingProblems(int limit) {
        return problemRepository.findTopBySubmissionCount(PageRequest.of(0, limit))
                .stream().map(problemMapper::toSummaryResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProblemSummaryResponse> getMostSolvedProblems(int limit) {
        return problemRepository.findTopBySolveCount(PageRequest.of(0, limit))
                .stream().map(problemMapper::toSummaryResponse).toList();
    }

    // Private helpers

    private Specification<Problem> buildCommonFilters(
            ProblemCategory category, ProblemDifficulty difficulty,
            Set<Long> tagIds, Integer minRating, Integer maxRating,
            String titleKeyword) {

        Specification<Problem> spec = Specification.<Problem>where(null);
        if (category != null)                                spec = spec.and(ProblemSpecification.hasCategory(category));
        if (difficulty != null)                              spec = spec.and(ProblemSpecification.hasDifficulty(difficulty));
        if (tagIds != null && !tagIds.isEmpty())             spec = spec.and(ProblemSpecification.hasAnyTag(tagIds));
        if (minRating != null || maxRating != null)          spec = spec.and(ProblemSpecification.ratingBetween(minRating, maxRating));
        if (titleKeyword != null && !titleKeyword.isBlank()) spec = spec.and(ProblemSpecification.titleContains(titleKeyword));
        return spec;
    }

    private void assertOwnerOrAdmin(Problem problem, Long callerId) {
        User caller = findUserOrThrow(callerId);
        boolean isOwner = problem.getAuthor() != null
                && problem.getAuthor().getId().equals(callerId);
        boolean isPrivileged = caller.getRole() == UserRole.ADMIN
                || caller.getRole() == UserRole.PROBLEM_SETTER;
        if (!isOwner && !isPrivileged) {
            throw new ForbiddenException("You do not have permission to modify this problem.");
        }
    }

    private Problem findProblemOrThrow(Long id) {
        return problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem", "id", id));
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    private void ensureProblemExists(Long id) {
        if (!problemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Problem", "id", id);
        }
    }

    private Set<Tag> resolveTags(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) return new HashSet<>();
        List<Tag> found = tagRepository.findAllById(tagIds);
        if (found.size() != tagIds.size()) {
            Set<Long> foundIds = new HashSet<>();
            found.forEach(t -> foundIds.add(t.getId()));
            tagIds.stream()
                    .filter(tid -> !foundIds.contains(tid))
                    .findFirst()
                    .ifPresent(missing -> {
                        throw new ResourceNotFoundException("Tag", "id", missing);
                    });
        }
        return new HashSet<>(found);
    }
}