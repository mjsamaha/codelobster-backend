package com.mjsamaha.codelobster.problem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TagCatalogSeeder implements CommandLineRunner {

    private final TagRepository tagRepository;

    public TagCatalogSeeder(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        for (ProblemTag problemTag : ProblemTag.values()) {
            if (!tagRepository.existsByNameIgnoreCaseAndType(problemTag.getDisplayName(), problemTag.getType())) {
                Tag tag = new Tag(problemTag.getDisplayName(), problemTag.getType());
                tagRepository.save(tag);
            }
        }
    }
}
