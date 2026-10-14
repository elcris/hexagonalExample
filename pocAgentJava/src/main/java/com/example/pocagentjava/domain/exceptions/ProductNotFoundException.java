package com.example.pocagentjava.domain.exceptions;

public final class ProductNotFoundException extends BusinessException {

  public ProductNotFoundException(Long productId) {
    super("PRODUCT_NOT_FOUND", "No existe un producto con id " + productId);
  }
}
