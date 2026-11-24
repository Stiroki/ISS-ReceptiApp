package com.ftn.iss.recepti.controller;

import com.ftn.iss.recepti.dto.CatalogDTOs.CatalogItemRequest;
import com.ftn.iss.recepti.model.*;
import com.ftn.iss.recepti.service.AdminCatalogService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/catalogs")
public class AdminCatalogController {

    private final AdminCatalogService catalogService;

    public AdminCatalogController(AdminCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // --- TIPOVI RECEPATA ---
    @GetMapping("/recipe-types")
    public Page<RecipeType> getRecipeTypes(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return catalogService.getRecipeTypes(search, pageable);
    }

    @PostMapping("/recipe-types")
    public ResponseEntity<RecipeType> createRecipeType(@Valid @RequestBody CatalogItemRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.createRecipeType(req.name()));
    }

    @PutMapping("/recipe-types/{id}")
    public ResponseEntity<RecipeType> updateRecipeType(@PathVariable Long id, @Valid @RequestBody CatalogItemRequest req) {
        return ResponseEntity.ok(catalogService.updateRecipeType(id, req.name()));
    }

    @DeleteMapping("/recipe-types/{id}")
    public ResponseEntity<Void> deleteRecipeType(@PathVariable Long id) {
        catalogService.deleteRecipeType(id);
        return ResponseEntity.noContent().build();
    }

    // --- KATEGORIJE NAMIRNICA ---
    @GetMapping("/categories")
    public Page<IngredientCategory> getCategories(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return catalogService.getCategories(search, pageable);
    }

    @PostMapping("/categories")
    public ResponseEntity<IngredientCategory> createCategory(@Valid @RequestBody CatalogItemRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.createCategory(req.name()));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<IngredientCategory> updateCategory(@PathVariable Long id, @Valid @RequestBody CatalogItemRequest req) {
        return ResponseEntity.ok(catalogService.updateCategory(id, req.name()));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        catalogService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // --- ALERGENI ---
    @GetMapping("/allergens")
    public Page<Allergen> getAllergens(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return catalogService.getAllergens(search, pageable);
    }

    @PostMapping("/allergens")
    public ResponseEntity<Allergen> createAllergen(@Valid @RequestBody CatalogItemRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.createAllergen(req.name()));
    }

    @PutMapping("/allergens/{id}")
    public ResponseEntity<Allergen> updateAllergen(@PathVariable Long id, @Valid @RequestBody CatalogItemRequest req) {
        return ResponseEntity.ok(catalogService.updateAllergen(id, req.name()));
    }

    @DeleteMapping("/allergens/{id}")
    public ResponseEntity<Void> deleteAllergen(@PathVariable Long id) {
        catalogService.deleteAllergen(id);
        return ResponseEntity.noContent().build();
    }

    // --- PREDLOZI KATEGORIJA ---
    @GetMapping("/proposals")
    public List<CategoryProposal> getPendingProposals() {
        return catalogService.getPendingProposals();
    }

    @PostMapping("/proposals/{id}/approve")
    public ResponseEntity<IngredientCategory> approveProposal(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.approveProposal(id));
    }
}