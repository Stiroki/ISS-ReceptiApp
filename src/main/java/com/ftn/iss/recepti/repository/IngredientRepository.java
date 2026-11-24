package com.ftn.iss.recepti.repository;

import com.ftn.iss.recepti.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}