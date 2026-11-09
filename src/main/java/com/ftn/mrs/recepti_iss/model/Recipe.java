package com.ftn.mrs.recepti_iss.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String story;

    private String mealType;
    private String dishType;
    private String dietType;

    private Integer defaultPortions;
    private Integer totalDurationMinutes;

    private String mainImageUrl;
    private String videoUrl;

    private String country;
    private String region;
    private String city;

    private Double averageRating = 0.0;
    private Integer ratingsCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    private LocalDateTime createdAt = LocalDateTime.now();
}