package com.ftn.iss.recepti.config;

import com.ftn.iss.recepti.model.*;
import com.ftn.iss.recepti.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final RecipeTypeRepository recipeTypeRepo;
    private final IngredientCategoryRepository categoryRepo;
    private final AllergenRepository allergenRepo;
    private final CategoryProposalRepository proposalRepo;
    private final IngredientRepository ingredientRepo;
    private final RecipeRepository recipeRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository userRepo,
                           RecipeTypeRepository recipeTypeRepo,
                           IngredientCategoryRepository categoryRepo,
                           AllergenRepository allergenRepo,
                           CategoryProposalRepository proposalRepo,
                           IngredientRepository ingredientRepo,
                           RecipeRepository recipeRepo,
                           PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.recipeTypeRepo = recipeTypeRepo;
        this.categoryRepo = categoryRepo;
        this.allergenRepo = allergenRepo;
        this.proposalRepo = proposalRepo;
        this.ingredientRepo = ingredientRepo;
        this.recipeRepo = recipeRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (userRepo.count() > 0) return;

        User milana = new User();
        milana.setUsername("milana_chef");
        milana.setEmail("milana@ftn.uns.ac.rs");
        milana.setPassword(encoder.encode("admin123"));
        milana.setRole(Role.ADMIN);
        milana.setRegion("Vojvodina, Srbija");
        milana.setBio("Ljubitelj domaće kuhinje i brzih recepata.");
        milana.setProfileImageUrl("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=300");

        User marko = new User();
        marko.setUsername("marko_kuvar");
        marko.setEmail("marko@ftn.uns.ac.rs");
        marko.setPassword(encoder.encode("user123"));
        marko.setRole(Role.USER);
        marko.setRegion("Šumadija, Srbija");
        marko.setBio("Tradicionalna jela i specijaliteti sa roštilja.");
        marko.setProfileImageUrl("https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=300");

        userRepo.saveAll(List.of(milana, marko));

        recipeTypeRepo.saveAll(List.of(
                new RecipeType("Doručak"),
                new RecipeType("Ručak"),
                new RecipeType("Večera"),
                new RecipeType("Deserti")
        ));

        Allergen gluten = allergenRepo.save(new Allergen("Gluten"));
        Allergen mleko = allergenRepo.save(new Allergen("Mleko"));
        Allergen jaja = allergenRepo.save(new Allergen("Jaja"));

        IngredientCategory zitarice = categoryRepo.save(new IngredientCategory("Žitarice"));
        IngredientCategory mlecni = categoryRepo.save(new IngredientCategory("Mlečni proizvodi"));
        IngredientCategory zacini = categoryRepo.save(new IngredientCategory("Začini"));

        proposalRepo.saveAll(List.of(
                new CategoryProposal("Biljna mleka i zamene", "marko_kuvar"),
                new CategoryProposal("Bezglutensko brašno", "milana_chef")
        ));

        Ingredient brasno = new Ingredient();
        brasno.setName("Brašno tip 400");
        brasno.setCategory(zitarice);
        brasno.setDefaultUnit("gram");
        brasno.setAllergens(Set.of(gluten));

        Ingredient mlekoIng = new Ingredient();
        mlekoIng.setName("Mleko 2.8%");
        mlekoIng.setCategory(mlecni);
        mlekoIng.setDefaultUnit("mililitar");
        mlekoIng.setAllergens(Set.of(mleko));

        Ingredient jajeIng = new Ingredient();
        jajeIng.setName("Jaja");
        jajeIng.setCategory(mlecni);
        jajeIng.setDefaultUnit("komad");
        jajeIng.setAllergens(Set.of(jaja));

        Ingredient so = new Ingredient();
        so.setName("So");
        so.setCategory(zacini);
        so.setDefaultUnit("po ukusu");

        ingredientRepo.saveAll(List.of(brasno, mlekoIng, jajeIng, so));

        Recipe hleb = new Recipe();
        hleb.setTitle("Domaći hleb iz rerne");
        hleb.setAuthor(milana);
        hleb.setAuthorRegion(milana.getRegion());
        hleb.setDishType("Pekarski proizvod");
        hleb.setServings(8);
        hleb.setTotalPrepTimeMinutes(90);
        hleb.setAverageRating(4.8);
        hleb.setRatingsCount(12);
        hleb.setCreatedAt(LocalDateTime.now().minusDays(5));
        hleb.setIngredients(List.of(
                new RecipeIngredient(hleb, brasno, 500.0, "gram"),
                new RecipeIngredient(hleb, so, 1.0, "kašičica")
        ));

        Recipe palacinke = new Recipe();
        palacinke.setTitle("Kremaste proteinske palačinke");
        palacinke.setAuthor(milana);
        palacinke.setAuthorRegion(milana.getRegion());
        palacinke.setDishType("Palačinke");
        palacinke.setServings(2);
        palacinke.setTotalPrepTimeMinutes(20);
        palacinke.setAverageRating(4.9);
        palacinke.setRatingsCount(8);
        palacinke.setCreatedAt(LocalDateTime.now().minusDays(1));
        palacinke.setIngredients(List.of(
                new RecipeIngredient(palacinke, brasno, 150.0, "gram"),
                new RecipeIngredient(palacinke, mlekoIng, 200.0, "mililitar"),
                new RecipeIngredient(palacinke, jajeIng, 2.0, "komad")
        ));

        recipeRepo.saveAll(List.of(hleb, palacinke));
    }
}