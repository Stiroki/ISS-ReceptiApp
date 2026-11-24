package com.ftn.iss.recepti.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDTOs {
    public record LoginRequest(
            @NotBlank(message = "Korisničko ime je obavezno") String username,
            @NotBlank(message = "Lozinka je obavezna") String password
    ) {}

    public record RegisterRequest(
            @NotBlank(message = "Korisničko ime je obavezno") @Size(min = 3, max = 30) String username,
            @NotBlank(message = "Mejl je obavezan") @Email(message = "Neispravan format mejl adrese") String email,
            @NotBlank(message = "Lozinka je obavezna") @Size(min = 6, message = "Lozinka mora imati bar 6 karaktera") String password,
            @NotBlank(message = "Region je obavezan") String region
    ) {}

    public record AuthResponse(String token, String username, String role) {}
}