package com.ftn.iss.recepti.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfileDTOs {
    public record UserProfileResponse(
            Long id,
            String username,
            String role,
            String profileImageUrl,
            String region,
            String bio,
            long publishedRecipesCount
    ) {}

    // Korisničko ime nije u zahtevu jer se ne može menjati (Stavka 2)
    public record UpdateProfileRequest(
            @NotBlank(message = "Region je obavezan") String region,
            @Size(max = 1000, message = "Biografija može imati najviše 1000 karaktera") String bio,
            String profileImageUrl
    ) {}
}