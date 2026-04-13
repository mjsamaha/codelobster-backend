package com.mjsamaha.codelobster.problem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByNameIgnoreCaseAndType(String name, TagType type);

    boolean existsByNameIgnoreCaseAndType(String name, TagType type);

    List<Tag> findAllByTypeOrderByNameAsc(TagType type);
}
