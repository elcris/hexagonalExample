package com.example.pocagentjava.domain.models.product;

public record CreateProductCommand(String name, String description, boolean active) {
}
