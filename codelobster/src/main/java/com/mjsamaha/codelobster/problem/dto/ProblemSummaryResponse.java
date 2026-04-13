package com.mjsamaha.codelobster.problem.dto;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Shown in paginated problem lists.
 * No description body, no sample I/O, no test case data.
 */
public record ProblemSummaryResponse(
        Long id,
        String title,
        String category,
        Set<TagResponse> tags,
        Integer rating,
        String difficulty,        // display name e.g. "Easy"
        String difficultyColor,   // hex e.g. "#22c55e"
        Integer solveCount,
        Integer submissionCount,
        double acceptanceRate,    // derived: solveCount / submissionCount
        LocalDateTime createdAt
) {}