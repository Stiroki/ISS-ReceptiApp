package com.ftn.iss.recepti.controller;

import com.ftn.iss.recepti.dto.RecipeHomeDTOs.PantryRecipeMatchResponse;
import com.ftn.iss.recepti.model.Ingredient;
import com.ftn.iss.recepti.model.Recipe;
import com.ftn.iss.recepti.repository.IngredientRepository;
import com.ftn.iss.recepti.service.RecipeHomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeHomeController {

    private final RecipeHomeService recipeHomeService;
    private final IngredientRepository ingredientRepo;

    public RecipeHomeController(RecipeHomeService recipeHomeService, IngredientRepository ingredientRepo) {
        this.recipeHomeService = recipeHomeService;
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping("/recipes/user/{username}")
    public List<Recipe> getRecipesByUser(@PathVariable String username) {
        return recipeHomeService.getRecipesByAuthor(username);
    }

    @GetMapping("/recipes/popular")
    public List<Recipe> getPopularRecipes() {
        return recipeHomeService.getPopularLast30Days();
    }

    @GetMapping("/recipes/latest")
    public List<Recipe> getLatestRecipes() {
        return recipeHomeService.getLatestRecipes();
    }

    @GetMapping("/recipes/by-ingredients")
    public List<PantryRecipeMatchResponse> getRecipesByIngredients(
            @RequestParam(required = false) List<Long> ingredientIds,
            @RequestParam(defaultValue = "0") int maxMissing) {
        return recipeHomeService.matchByAvailableIngredients(ingredientIds, maxMissing);
    }

    @GetMapping("/catalogs/ingredients")
    public List<Ingredient> getAllIngredients() {
        return ingredientRepo.findAll();
    }
}