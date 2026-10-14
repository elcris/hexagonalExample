package com.example.pocagentjava.domain.models.product;

import com.example.pocagentjava.domain.exceptions.InvalidBusinessRuleException;
import java.util.Objects;

public record Product(Long id, String name, String description, boolean active) {

  public Product {
    if (id != null && id <= 0) {
      throw new InvalidBusinessRuleException("PRODUCT_ID_INVALID", "El id debe ser positivo");
    }
    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(description, "description");
    if (name.isBlank()) {
      throw new InvalidBusinessRuleException("PRODUCT_NAME_REQUIRED", "El nombre es obligatorio");
    }
    if (name.length() > 120) {
      throw new InvalidBusinessRuleException(
          "PRODUCT_NAME_TOO_LONG", "El nombre no puede superar 120 caracteres");
    }
    if (description.length() > 1000) {
      throw new InvalidBusinessRuleException(
          "PRODUCT_DESCRIPTION_TOO_LONG", "La descripción no puede superar 1000 caracteres");
    }
  }
}
