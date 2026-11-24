package com.ftn.iss.recepti.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_types")
public class RecipeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public RecipeType() {}
    public RecipeType(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}