package com.example.pocagentjava.infrastructure.adapters.inbound.rest.v1.controller;

import com.example.pocagentjava.domain.models.product.GetProductQuery;
import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.domain.ports.inbound.CreateProductUseCase;
import com.example.pocagentjava.domain.ports.inbound.DeleteProductUseCase;
import com.example.pocagentjava.domain.ports.inbound.GetProductUseCase;
import com.example.pocagentjava.domain.ports.inbound.ListProductsUseCase;
import com.example.pocagentjava.domain.ports.inbound.UpdateProductUseCase;
import com.example.pocagentjava.infrastructure.adapters.inbound.rest.v1.dto.CreateProductRequest;
import com.example.pocagentjava.infrastructure.adapters.inbound.rest.v1.dto.ProductResponse;
import com.example.pocagentjava.infrastructure.adapters.inbound.rest.v1.dto.UpdateProductRequest;
import com.example.pocagentjava.infrastructure.common.mapper.ProductRestMapper;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private final CreateProductUseCase createProductUseCase;
  private final ListProductsUseCase listProductsUseCase;
  private final GetProductUseCase getProductUseCase;
  private final UpdateProductUseCase updateProductUseCase;
  private final DeleteProductUseCase deleteProductUseCase;
  private final ProductRestMapper mapper;

  public ProductController(
      CreateProductUseCase createProductUseCase,
      ListProductsUseCase listProductsUseCase,
      GetProductUseCase getProductUseCase,
      UpdateProductUseCase updateProductUseCase,
      DeleteProductUseCase deleteProductUseCase,
      ProductRestMapper mapper) {
    this.createProductUseCase = createProductUseCase;
    this.listProductsUseCase = listProductsUseCase;
    this.getProductUseCase = getProductUseCase;
    this.updateProductUseCase = updateProductUseCase;
    this.deleteProductUseCase = deleteProductUseCase;
    this.mapper = mapper;
  }

  @PostMapping
  public ResponseEntity<ProductResponse> create(
      @Valid @RequestBody CreateProductRequest request) {
    Product product = createProductUseCase.create(mapper.toCommand(request));
    URI location = URI.create("/api/v1/products/" + product.id());
    return ResponseEntity.created(location).body(mapper.toResponse(product));
  }

  @GetMapping
  public List<ProductResponse> list() {
    return listProductsUseCase.list().stream().map(mapper::toResponse).toList();
  }

  @GetMapping("/{id}")
  public ProductResponse get(@PathVariable Long id) {
    return mapper.toResponse(getProductUseCase.get(new GetProductQuery(id)));
  }

  @PutMapping("/{id}")
  public ProductResponse update(
      @PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
    return mapper.toResponse(updateProductUseCase.update(mapper.toCommand(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteProductUseCase.delete(id);
    return ResponseEntity.noContent().build();
  }
}
