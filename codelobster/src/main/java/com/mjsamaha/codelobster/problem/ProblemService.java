package com.mjsamaha.codelobster.problem;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mjsamaha.codelobster.problem.dto.CreateProblemRequest;
import com.mjsamaha.codelobster.problem.dto.ProblemAdminDetailResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemDetailResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemStatsResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemSummaryResponse;
import com.mjsamaha.codelobster.problem.dto.UpdateProblemRequest;

public interface ProblemService {

    // Crud

    ProblemAdminDetailResponse createProblem(CreateProblemRequest request, Long authorId);

    ProblemAdminDetailResponse updateProblem(Long id, UpdateProblemRequest request, Long authorId);

    void deleteProblem(Long id, Long authorId);

    //Publication management

    ProblemAdminDetailResponse publishProblem(Long id, Long callerId);

    ProblemAdminDetailResponse unpublishProblem(Long id, Long callerId);

    // Public reads

    Page<ProblemSummaryResponse> getPublishedProblems(
            ProblemCategory category,
            ProblemDifficulty difficulty,
            Set<Long> tagIds,
            Integer minRating,
            Integer maxRating,
            String titleKeyword,
            Pageable pageable);

    ProblemDetailResponse getPublishedProblemById(Long id);

    // Admin reads (includes unpublished problems and more details)

    Page<ProblemSummaryResponse> getAllProblems(
            ProblemCategory category,
            ProblemDifficulty difficulty,
            Set<Long> tagIds,
            Integer minRating,
            Integer maxRating,
            String titleKeyword,
            Boolean isPublished,
            Long authorId,
            Pageable pageable);

    ProblemAdminDetailResponse getAdminProblemById(Long id);

    // Tag management

    ProblemAdminDetailResponse addTags(Long problemId, Set<Long> tagIds);

    ProblemAdminDetailResponse removeTags(Long problemId, Set<Long> tagIds);

    // Stats

    ProblemStatsResponse getProblemStats(Long id);

    void incrementSolveCount(Long problemId);

    void incrementSubmissionCount(Long problemId);

    void incrementAttemptCount(Long problemId);

    // Trending

    List<ProblemSummaryResponse> getTrendingProblems(int limit);

    List<ProblemSummaryResponse> getMostSolvedProblems(int limit);
}