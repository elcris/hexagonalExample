package com.example.pocagentjava.infrastructure.adapters.outbound.persistence.mapper;

import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.infrastructure.adapters.outbound.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {

  public ProductEntity toEntity(Product product) {
    return new ProductEntity(
        product.id(), product.name(), product.description(), product.active());
  }

  public Product toDomain(ProductEntity entity) {
    return new Product(
        entity.getId(), entity.getName(), entity.getDescription(), entity.isActive());
  }
}
