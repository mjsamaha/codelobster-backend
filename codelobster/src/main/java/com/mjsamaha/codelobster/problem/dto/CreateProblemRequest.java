package com.mjsamaha.codelobster.problem.dto;

import java.util.Set;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Used by a problem-setter or admin to create a new problem draft.
 * Difficulty is intentionally absent — it is derived from rating server-side.
 */
public record CreateProblemRequest(

        @NotBlank
        @Size(max = 100)
        String title,

        @NotBlank
        String description,

        @NotNull
        ProblemCategoryRequest category,

        /** Tag IDs that must already exist in the tags table. */
        Set<Long> tagIds,

        @NotNull
        @Min(400) @Max(3900)
        Integer rating,

        @NotNull
        @Min(500) @Max(15_000)
        Integer timeLimit,   // milliseconds

        @NotNull
        @Min(16) @Max(1024)
        Integer memoryLimit, // megabytes

        // Problem statement sections (nullable — can be filled in later)
        String inputFormat,
        String outputFormat,
        String constraints,
        String sampleInput,
        String sampleOutput,
        String sampleExplanation
) {}