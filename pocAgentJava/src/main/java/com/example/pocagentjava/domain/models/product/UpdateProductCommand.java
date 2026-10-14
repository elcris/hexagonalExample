package com.example.pocagentjava.domain.models.product;

public record UpdateProductCommand(
    Long id, String name, String description, boolean active) {
}
