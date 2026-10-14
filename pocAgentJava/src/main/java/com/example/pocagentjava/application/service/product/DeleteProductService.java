package com.example.pocagentjava.application.service.product;

import com.example.pocagentjava.domain.exceptions.ProductNotFoundException;
import com.example.pocagentjava.domain.ports.inbound.DeleteProductUseCase;
import com.example.pocagentjava.domain.ports.outbound.ProductPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteProductService implements DeleteProductUseCase {

  private final ProductPersistencePort productPersistencePort;

  public DeleteProductService(ProductPersistencePort productPersistencePort) {
    this.productPersistencePort = productPersistencePort;
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (productPersistencePort.findById(id).isEmpty()) {
      throw new ProductNotFoundException(id);
    }
    productPersistencePort.deleteById(id);
  }
}
