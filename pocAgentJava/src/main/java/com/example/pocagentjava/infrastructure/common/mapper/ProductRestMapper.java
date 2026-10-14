package com.example.pocagentjava.infrastructure.common.mapper;

import com.example.pocagentjava.domain.models.product.CreateProductCommand;
import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.domain.models.product.UpdateProductCommand;
import com.example.pocagentjava.infrastructure.adapters.inbound.rest.v1.dto.CreateProductRequest;
import com.example.pocagentjava.infrastructure.adapters.inbound.rest.v1.dto.ProductResponse;
import com.example.pocagentjava.infrastructure.adapters.inbound.rest.v1.dto.UpdateProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductRestMapper {

  public CreateProductCommand toCommand(CreateProductRequest request) {
    return new CreateProductCommand(request.name(), request.description(), request.active());
  }

  public UpdateProductCommand toCommand(Long id, UpdateProductRequest request) {
    return new UpdateProductCommand(id, request.name(), request.description(), request.active());
  }

  public ProductResponse toResponse(Product product) {
    return new ProductResponse(
        product.id(), product.name(), product.description(), product.active());
  }
}
