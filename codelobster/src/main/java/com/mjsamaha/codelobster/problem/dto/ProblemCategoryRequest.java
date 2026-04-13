package com.mjsamaha.codelobster.problem.dto;

/**
 * Mirrors ProblemCategory enum values.
 * Kept as a separate type so the API surface is decoupled from the internal enum name.
 */
public enum ProblemCategoryRequest {
    IMPLEMENTATION,
    DATA_STRUCTURES,
    ALGORITHMS,
    MATHEMATICS,
    GRAPHS,
    STRINGS,
    DYNAMIC_PROGRAMMING,
    GREEDY,
    SEARCH,
    GEOMETRY,
    BIT_MANIPULATION,
    MISCELLANEOUS
}