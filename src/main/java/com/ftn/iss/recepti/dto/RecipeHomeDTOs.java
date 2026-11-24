package com.ftn.iss.recepti.dto;

import com.ftn.iss.recepti.model.Recipe;
import java.util.List;

public class RecipeHomeDTOs {
    public record PantryRecipeMatchResponse(
            Recipe recipe,
            int missingIngredientsCount,
            List<String> missingIngredientNames,
            boolean canPrepareImmediately
    ) {}
}