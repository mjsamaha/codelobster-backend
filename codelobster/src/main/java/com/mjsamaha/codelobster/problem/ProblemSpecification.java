package com.mjsamaha.codelobster.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
/**
 * Factory of JPA Specifications for Problem filtering.
 * Combine freely — all predicates are AND-ed together.
 */
public final class ProblemSpecification {
	
	private ProblemSpecification() {}

    public static Specification<Problem> publishedOnly() {
        return (root, query, cb) -> cb.isTrue(root.get("isPublished"));
    }

    public static Specification<Problem> hasCategory(ProblemCategory category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    public static Specification<Problem> hasDifficulty(ProblemDifficulty difficulty) {
        return (root, query, cb) -> cb.equal(root.get("difficulty"), difficulty);
    }

    public static Specification<Problem> ratingBetween(Integer min, Integer max) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (min != null) predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), min));
            if (max != null) predicates.add(cb.lessThanOrEqualTo(root.get("rating"), max));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Problem> hasAnyTag(Set<Long> tagIds) {
        return (root, query, cb) -> {
            Join<Problem, Tag> tags = root.join("tags");
            query.distinct(true);
            return tags.get("id").in(tagIds);
        };
    }

    public static Specification<Problem> titleContains(String keyword) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")),
                        "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<Problem> byAuthor(Long authorId) {
        return (root, query, cb) -> cb.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<Problem> isPublished(Boolean published) {
        return (root, query, cb) -> cb.equal(root.get("isPublished"), published);
    }

}
