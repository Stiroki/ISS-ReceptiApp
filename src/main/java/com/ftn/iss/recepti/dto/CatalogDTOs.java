package com.ftn.iss.recepti.dto;

import jakarta.validation.constraints.NotBlank;

public class CatalogDTOs {
    public record CatalogItemRequest(
            @NotBlank(message = "Naziv stavke je obavezan") String name
    ) {}
}