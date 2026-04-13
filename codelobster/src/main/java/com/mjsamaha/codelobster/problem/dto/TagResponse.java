package com.mjsamaha.codelobster.problem.dto;

/**
 * Lightweight tag projection included in all problem responses.
 */
public record TagResponse(
        Long id,
        String name,
        String type   // TagType name
) {}