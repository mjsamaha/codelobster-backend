package com.mjsamaha.codelobster.problem.dto;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Returned to any authenticated user viewing a published problem.
 * Test case input/output data is intentionally excluded.
 */
public record ProblemDetailResponse(
        Long id,
        String title,
        String description,
        String category,
        Set<TagResponse> tags,
        Integer rating,
        String difficulty,
        String difficultyColor,
        Integer timeLimit,
        Integer memoryLimit,
        String inputFormat,
        String outputFormat,
        String constraints,
        String sampleInput,
        String sampleOutput,
        String sampleExplanation,
        ProblemStatsResponse stats,
        String authorUsername,
        LocalDateTime createdAt
) {}