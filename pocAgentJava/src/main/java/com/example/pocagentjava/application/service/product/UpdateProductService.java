package com.example.pocagentjava.application.service.product;

import com.example.pocagentjava.domain.exceptions.ProductNotFoundException;
import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.domain.models.product.UpdateProductCommand;
import com.example.pocagentjava.domain.ports.inbound.UpdateProductUseCase;
import com.example.pocagentjava.domain.ports.outbound.ProductPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProductService implements UpdateProductUseCase {

  private final ProductPersistencePort productPersistencePort;

  public UpdateProductService(ProductPersistencePort productPersistencePort) {
    this.productPersistencePort = productPersistencePort;
  }

  @Override
  @Transactional
  public Product update(UpdateProductCommand command) {
    if (productPersistencePort.findById(command.id()).isEmpty()) {
      throw new ProductNotFoundException(command.id());
    }
    Product product = new Product(
        command.id(), command.name(), command.description(), command.active());
    return productPersistencePort.save(product);
  }
}
