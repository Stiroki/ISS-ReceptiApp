package com.ftn.iss.recepti.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    // Region autora u trenutku objave recepta (ne menja se kada korisnik promeni region na profilu)
    @Column(nullable = false)
    private String authorRegion;

    private String dishType;
    private int servings;
    private int totalPrepTimeMinutes;
    private String mainImageUrl;

    @Column(length = 2000)
    private String story;

    private double averageRating;
    private int ratingsCount;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    public Recipe() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    public String getAuthorRegion() { return authorRegion; }
    public void setAuthorRegion(String authorRegion) { this.authorRegion = authorRegion; }
    public String getDishType() { return dishType; }
    public void setDishType(String dishType) { this.dishType = dishType; }
    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }
    public int getTotalPrepTimeMinutes() { return totalPrepTimeMinutes; }
    public void setTotalPrepTimeMinutes(int totalPrepTimeMinutes) { this.totalPrepTimeMinutes = totalPrepTimeMinutes; }
    public String getMainImageUrl() { return mainImageUrl; }
    public void setMainImageUrl(String mainImageUrl) { this.mainImageUrl = mainImageUrl; }
    public String getStory() { return story; }
    public void setStory(String story) { this.story = story; }
    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
    public int getRatingsCount() { return ratingsCount; }
    public void setRatingsCount(int ratingsCount) { this.ratingsCount = ratingsCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<RecipeIngredient> getIngredients() { return ingredients; }
    public void setIngredients(List<RecipeIngredient> ingredients) { this.ingredients = ingredients; }
}