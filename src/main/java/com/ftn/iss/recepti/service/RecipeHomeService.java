package com.ftn.iss.recepti.service;

import com.ftn.iss.recepti.dto.RecipeHomeDTOs.PantryRecipeMatchResponse;
import com.ftn.iss.recepti.model.Recipe;
import com.ftn.iss.recepti.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeHomeService {

    private final RecipeRepository recipeRepo;

    public RecipeHomeService(RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public List<Recipe> getRecipesByAuthor(String username) {
        return recipeRepo.findByAuthorUsernameOrderByCreatedAtDesc(username);
    }

    public List<Recipe> getPopularLast30Days() {
        return recipeRepo.findByCreatedAtAfterOrderByAverageRatingDescRatingsCountDesc(
                LocalDateTime.now().minusDays(30)
        );
    }

    public List<Recipe> getLatestRecipes() {
        return recipeRepo.findAllByOrderByCreatedAtDesc();
    }

    public List<PantryRecipeMatchResponse> matchByAvailableIngredients(List<Long> availableIds, int maxMissing) {
        Set<Long> availableSet = availableIds == null ? Set.of() : new HashSet<>(availableIds);

        return recipeRepo.findAll().stream()
                .map(recipe -> {
                    List<String> missingNames = recipe.getIngredients().stream()
                            .filter(ri -> !availableSet.contains(ri.getIngredient().getId()))
                            .map(ri -> ri.getIngredient().getName())
                            .toList();
                    int missingCount = missingNames.size();
                    return new PantryRecipeMatchResponse(recipe, missingCount, missingNames, missingCount == 0);
                })
                .filter(match -> match.missingIngredientsCount() <= maxMissing)
                .sorted(Comparator
                        .comparingInt(PantryRecipeMatchResponse::missingIngredientsCount)
                        .thenComparing(m -> m.recipe().getAverageRating(), Comparator.reverseOrder()))
                .toList();
    }
}