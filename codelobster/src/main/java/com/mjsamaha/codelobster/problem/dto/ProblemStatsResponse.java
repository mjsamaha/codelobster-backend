package com.mjsamaha.codelobster.problem.dto;

/**
 * Stats snapshot for a problem.
 * acceptanceRate is derived: solveCount / submissionCount (0.0 if no submissions).
 * Can be returned standalone (e.g. for a stats widget) or embedded in detail responses.
 */
public record ProblemStatsResponse(
        Integer solveCount,
        Integer submissionCount,
        Integer attemptCount,
        double acceptanceRate
) {}