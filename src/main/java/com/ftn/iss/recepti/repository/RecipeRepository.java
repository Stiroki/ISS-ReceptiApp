package com.ftn.iss.recepti.repository;

import com.ftn.iss.recepti.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    long countByAuthorId(Long authorId);
    List<Recipe> findByAuthorUsernameOrderByCreatedAtDesc(String username);
    List<Recipe> findAllByOrderByCreatedAtDesc();
    List<Recipe> findByCreatedAtAfterOrderByAverageRatingDescRatingsCountDesc(LocalDateTime since);
}