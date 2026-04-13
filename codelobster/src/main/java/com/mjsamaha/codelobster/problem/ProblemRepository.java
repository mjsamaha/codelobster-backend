package com.mjsamaha.codelobster.problem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, JpaSpecificationExecutor<Problem> {
	
	// Lookup

	Optional<Problem> findByTitle(String title);

    boolean existsByTitle(String title);

    /** Case-insensitive title search (published only). */
    @Query("""
            SELECT p FROM Problem p
            WHERE p.isPublished = true
              AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Problem> searchPublishedByTitle(@Param("keyword") String keyword, Pageable pageable);

    /** Case-insensitive title search (admin — all problems). */
    @Query("""
            SELECT p FROM Problem p
            WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Problem> searchAllByTitle(@Param("keyword") String keyword, Pageable pageable);

    // Published problem queries (for general user browsing)

    Page<Problem> findAllByIsPublishedTrue(Pageable pageable);

    Page<Problem> findAllByIsPublishedTrueAndCategory(ProblemCategory category, Pageable pageable);

    Page<Problem> findAllByIsPublishedTrueAndDifficulty(ProblemDifficulty difficulty, Pageable pageable);

    Page<Problem> findAllByIsPublishedTrueAndCategoryAndDifficulty(
            ProblemCategory category, ProblemDifficulty difficulty, Pageable pageable);

    // Rating Range Filtering (published only)

    Page<Problem> findAllByIsPublishedTrueAndRatingBetween(
            Integer minRating, Integer maxRating, Pageable pageable);

    // Tag Filtering

    @Query("""
            SELECT DISTINCT p FROM Problem p
            JOIN p.tags t
            WHERE p.isPublished = true
              AND t.id IN :tagIds
            """)
    Page<Problem> findAllByIsPublishedTrueAndTagIds(
            @Param("tagIds") List<Long> tagIds, Pageable pageable);

    // Author-specific queries (for problem management)

    Page<Problem> findAllByAuthorId(Long authorId, Pageable pageable);

    Page<Problem> findAllByAuthorIdAndIsPublished(Long authorId, Boolean isPublished, Pageable pageable);
    
    /** Top N published problems by solve count (homepage "Most Solved"). */
    @Query("""
            SELECT p FROM Problem p
            WHERE p.isPublished = true
            ORDER BY p.solveCount DESC
            """)
    List<Problem> findTopBySolveCount(Pageable pageable);

    /** Top N published problems by submission count (homepage "Trending"). */
    @Query("""
            SELECT p FROM Problem p
            WHERE p.isPublished = true
            ORDER BY p.submissionCount DESC
            """)
    List<Problem> findTopBySubmissionCount(Pageable pageable);

    // Statistics updates

    @Modifying
    @Query("UPDATE Problem p SET p.solveCount = p.solveCount + 1 WHERE p.id = :id")
    void incrementSolveCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Problem p SET p.submissionCount = p.submissionCount + 1 WHERE p.id = :id")
    void incrementSubmissionCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Problem p SET p.attemptCount = p.attemptCount + 1 WHERE p.id = :id")
    void incrementAttemptCount(@Param("id") Long id);

}
