package com.mjsamaha.codelobster.problem.dto;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Returned only to admins or the problem's author.
 * Includes publication state and internal counters not shown publicly.
 */
public record ProblemAdminDetailResponse(
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
        Boolean isPublished,
        String inputFormat,
        String outputFormat,
        String constraints,
        String sampleInput,
        String sampleOutput,
        String sampleExplanation,
        ProblemStatsResponse stats,
        Long authorId,
        String authorUsername,
        LocalDateTime createdAt
) {}