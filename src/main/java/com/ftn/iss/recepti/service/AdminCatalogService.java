package com.ftn.iss.recepti.service;

import com.ftn.iss.recepti.exception.BadRequestException;
import com.ftn.iss.recepti.exception.ConflictException;
import com.ftn.iss.recepti.exception.ResourceNotFoundException;
import com.ftn.iss.recepti.model.*;
import com.ftn.iss.recepti.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminCatalogService {

    private final RecipeTypeRepository recipeTypeRepo;
    private final IngredientCategoryRepository categoryRepo;
    private final AllergenRepository allergenRepo;
    private final CategoryProposalRepository proposalRepo;

    public AdminCatalogService(RecipeTypeRepository recipeTypeRepo,
                               IngredientCategoryRepository categoryRepo,
                               AllergenRepository allergenRepo,
                               CategoryProposalRepository proposalRepo) {
        this.recipeTypeRepo = recipeTypeRepo;
        this.categoryRepo = categoryRepo;
        this.allergenRepo = allergenRepo;
        this.proposalRepo = proposalRepo;
    }

    // --- TIPOVI RECEPATA ---
    public Page<RecipeType> getRecipeTypes(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return recipeTypeRepo.findByNameContainingIgnoreCase(search.trim(), pageable);
        }
        return recipeTypeRepo.findAll(pageable);
    }

    public RecipeType createRecipeType(String name) {
        if (recipeTypeRepo.existsByNameIgnoreCase(name.trim())) {
            throw new ConflictException("Tip recepta već postoji.");
        }
        return recipeTypeRepo.save(new RecipeType(name.trim()));
    }

    public RecipeType updateRecipeType(Long id, String name) {
        RecipeType rt = recipeTypeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tip recepta nije pronađen."));
        rt.setName(name.trim());
        return recipeTypeRepo.save(rt);
    }

    public void deleteRecipeType(Long id) {
        if (!recipeTypeRepo.existsById(id)) throw new ResourceNotFoundException("Tip recepta nije pronađen.");
        recipeTypeRepo.deleteById(id);
    }

    // --- KATEGORIJE NAMIRNICA ---
    public Page<IngredientCategory> getCategories(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return categoryRepo.findByNameContainingIgnoreCase(search.trim(), pageable);
        }
        return categoryRepo.findAll(pageable);
    }

    public IngredientCategory createCategory(String name) {
        if (categoryRepo.existsByNameIgnoreCase(name.trim())) {
            throw new ConflictException("Kategorija namirnica već postoji.");
        }
        return categoryRepo.save(new IngredientCategory(name.trim()));
    }

    public IngredientCategory updateCategory(Long id, String name) {
        IngredientCategory cat = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategorija nije pronađena."));
        cat.setName(name.trim());
        return categoryRepo.save(cat);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepo.existsById(id)) throw new ResourceNotFoundException("Kategorija nije pronađena.");
        categoryRepo.deleteById(id);
    }

    // --- ALERGENI ---
    public Page<Allergen> getAllergens(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return allergenRepo.findByNameContainingIgnoreCase(search.trim(), pageable);
        }
        return allergenRepo.findAll(pageable);
    }

    public Allergen createAllergen(String name) {
        if (allergenRepo.existsByNameIgnoreCase(name.trim())) {
            throw new ConflictException("Alergen već postoji.");
        }
        return allergenRepo.save(new Allergen(name.trim()));
    }

    public Allergen updateAllergen(Long id, String name) {
        Allergen allergen = allergenRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alergen nije pronađen."));
        allergen.setName(name.trim());
        return allergenRepo.save(allergen);
    }

    public void deleteAllergen(Long id) {
        if (!allergenRepo.existsById(id)) throw new ResourceNotFoundException("Alergen nije pronađen.");
        allergenRepo.deleteById(id);
    }

    // --- PREDLOZI KATEGORIJA ---
    public List<CategoryProposal> getPendingProposals() {
        return proposalRepo.findByStatus(ProposalStatus.PENDING);
    }

    @Transactional
    public IngredientCategory approveProposal(Long proposalId) {
        CategoryProposal proposal = proposalRepo.findById(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("Predlog nije pronađen."));
        if (proposal.getStatus() != ProposalStatus.PENDING) {
            throw new BadRequestException("Predlog je već obrađen.");
        }
        proposal.setStatus(ProposalStatus.APPROVED);
        proposalRepo.save(proposal);

        if (categoryRepo.existsByNameIgnoreCase(proposal.getProposedName())) {
            throw new ConflictException("Kategorija '" + proposal.getProposedName() + "' već postoji.");
        }
        return categoryRepo.save(new IngredientCategory(proposal.getProposedName()));
    }
}