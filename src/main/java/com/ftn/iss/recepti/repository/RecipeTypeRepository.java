package com.ftn.iss.recepti.repository;

import com.ftn.iss.recepti.model.RecipeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeTypeRepository extends JpaRepository<RecipeType, Long> {
    Page<RecipeType> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByNameIgnoreCase(String name);
}