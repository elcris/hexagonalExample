package com.example.pocagentjava.application.service.product;

import com.example.pocagentjava.domain.models.product.CreateProductCommand;
import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.domain.ports.inbound.CreateProductUseCase;
import com.example.pocagentjava.domain.ports.outbound.ProductPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductService implements CreateProductUseCase {

  private final ProductPersistencePort productPersistencePort;

  public CreateProductService(ProductPersistencePort productPersistencePort) {
    this.productPersistencePort = productPersistencePort;
  }

  @Override
  @Transactional
  public Product create(CreateProductCommand command) {
    Product product = new Product(null, command.name(), command.description(), command.active());
    return productPersistencePort.save(product);
  }
}
