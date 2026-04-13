package com.mjsamaha.codelobster.problem;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mjsamaha.codelobster.problem.dto.CreateProblemRequest;
import com.mjsamaha.codelobster.problem.dto.ProblemAdminDetailResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemDetailResponse;
import com.mjsamaha.codelobster.problem.dto.ProblemSummaryResponse;
import com.mjsamaha.codelobster.problem.dto.TagResponse;
import com.mjsamaha.codelobster.problem.dto.UpdateProblemRequest;
import com.mjsamaha.codelobster.security.CustomUserDetails;

import jakarta.validation.Valid;

@RestController
@Validated
public class ProblemController {

    private final ProblemService problemService;
    private final TagRepository tagRepository;

    public ProblemController(ProblemService problemService, TagRepository tagRepository) {
        this.problemService = problemService;
        this.tagRepository = tagRepository;
    }

    // Public endpoints for all users (including unauthenticated) to browse published problems

    /**
     * GET /api/problems/public
     * Paginated, filterable list of all published problems.
     */
    @GetMapping("/api/problems/public")
    public ResponseEntity<Page<ProblemSummaryResponse>> getPublishedProblems(
            @RequestParam(required = false) ProblemCategory category,
            @RequestParam(required = false) ProblemDifficulty difficulty,
            @RequestParam(required = false) Set<Long> tagIds,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(required = false) String titleKeyword,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {

        return ResponseEntity.ok(problemService.getPublishedProblems(
                category, difficulty, tagIds, minRating, maxRating, titleKeyword, pageable));
    }

    /**
     * GET /api/problems/public/{id}
     * Full detail view of a single published problem.
     */
    @GetMapping("/api/problems/public/{id}")
    public ResponseEntity<ProblemDetailResponse> getPublishedProblemById(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.getPublishedProblemById(id));
    }

    /**
     * GET /api/problems/public/tags
     * All available tags, optionally filtered by TagType.
     */
    @GetMapping("/api/problems/public/tags")
    public ResponseEntity<List<TagResponse>> getAllTags(
            @RequestParam(required = false) TagType type) {

        List<Tag> tags = (type != null)
                ? tagRepository.findAllByTypeOrderByNameAsc(type)
                : tagRepository.findAll();

        List<TagResponse> response = tags.stream()
                .map(t -> new TagResponse(t.getId(), t.getName(), t.getType().name()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/problems/public/categories
     * All available problem categories derived from the ProblemCategory enum.
     */
    @GetMapping("/api/problems/public/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = Arrays.stream(ProblemCategory.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    // Admin endpoints for managing problems (requires authentication and appropriate roles)

    /**
     * POST /api/admin/problems
     * Create a new problem draft. Caller becomes the author.
     */
    @PostMapping("/api/admin/problems")
    public ResponseEntity<ProblemAdminDetailResponse> createProblem(
            @Valid @RequestBody CreateProblemRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(problemService.createProblem(request, currentUser.getUserId()));
    }

    /**
     * PATCH /api/admin/problems/{id}
     * Partial update. Only non-null fields are applied.
     * Caller must be the author, PROBLEM_SETTER, or ADMIN.
     */
    @PatchMapping("/api/admin/problems/{id}")
    public ResponseEntity<ProblemAdminDetailResponse> updateProblem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProblemRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        return ResponseEntity.ok(
                problemService.updateProblem(id, request, currentUser.getUserId()));
    }

    /**
     * PATCH /api/admin/problems/{id}/publish
     * Marks the problem as published (visible to all users).
     */
    @PatchMapping("/api/admin/problems/{id}/publish")
    public ResponseEntity<ProblemAdminDetailResponse> publishProblem(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.publishProblem(id));
    }

    /**
     * PATCH /api/admin/problems/{id}/unpublish
     * Marks the problem as unpublished (draft mode).
     */
    @PatchMapping("/api/admin/problems/{id}/unpublish")
    public ResponseEntity<ProblemAdminDetailResponse> unpublishProblem(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.unpublishProblem(id));
    }

    /**
     * DELETE /api/admin/problems/{id}
     * Permanently deletes a problem.
     * Caller must be the author, PROBLEM_SETTER, or ADMIN.
     */
    @DeleteMapping("/api/admin/problems/{id}")
    public ResponseEntity<Void> deleteProblem(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        problemService.deleteProblem(id, currentUser.getUserId());
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/admin/problems
     * Paginated list of ALL problems (published and drafts), with full filter set.
     */
    @GetMapping("/api/admin/problems")
    public ResponseEntity<Page<ProblemSummaryResponse>> getAllProblems(
            @RequestParam(required = false) ProblemCategory category,
            @RequestParam(required = false) ProblemDifficulty difficulty,
            @RequestParam(required = false) Set<Long> tagIds,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(required = false) String titleKeyword,
            @RequestParam(required = false) Boolean isPublished,
            @RequestParam(required = false) Long authorId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {

        return ResponseEntity.ok(problemService.getAllProblems(
                category, difficulty, tagIds, minRating, maxRating,
                titleKeyword, isPublished, authorId, pageable));
    }

    /**
     * GET /api/admin/problems/{id}
     * Full admin detail view of any problem, published or draft.
     */
    @GetMapping("/api/admin/problems/{id}")
    public ResponseEntity<ProblemAdminDetailResponse> getAdminProblemById(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.getAdminProblemById(id));
    }
}
