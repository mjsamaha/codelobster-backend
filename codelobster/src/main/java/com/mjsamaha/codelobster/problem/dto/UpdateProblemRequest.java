package com.mjsamaha.codelobster.problem.dto;

import java.util.Set;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Partial-update DTO — every field is nullable.
 * Only non-null fields are applied in the service.
 * Difficulty is intentionally absent — always re-derived from rating.
 */
public record UpdateProblemRequest(

        @Size(max = 100)
        String title,

        String description,

        ProblemCategoryRequest category,

        /** Replaces the full tag set when present. */
        Set<Long> tagIds,

        @Min(400) @Max(3900)
        Integer rating,

        @Min(500) @Max(15_000)
        Integer timeLimit,

        @Min(16) @Max(1024)
        Integer memoryLimit,

        String inputFormat,
        String outputFormat,
        String constraints,
        String sampleInput,
        String sampleOutput,
        String sampleExplanation
) {}