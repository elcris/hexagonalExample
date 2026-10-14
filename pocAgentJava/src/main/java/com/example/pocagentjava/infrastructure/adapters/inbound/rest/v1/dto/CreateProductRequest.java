package com.example.pocagentjava.infrastructure.adapters.inbound.rest.v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
    @NotBlank @Size(max = 120) String name,
    @NotNull @Size(max = 1000) String description,
    @NotNull Boolean active) {
}
