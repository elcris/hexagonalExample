package com.example.pocagentjava.application.service.product;

import com.example.pocagentjava.domain.exceptions.ProductNotFoundException;
import com.example.pocagentjava.domain.models.product.GetProductQuery;
import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.domain.ports.inbound.GetProductUseCase;
import com.example.pocagentjava.domain.ports.outbound.ProductPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetProductService implements GetProductUseCase {

  private final ProductPersistencePort productPersistencePort;

  public GetProductService(ProductPersistencePort productPersistencePort) {
    this.productPersistencePort = productPersistencePort;
  }

  @Override
  @Transactional(readOnly = true)
  public Product get(GetProductQuery query) {
    return productPersistencePort.findById(query.id())
        .orElseThrow(() -> new ProductNotFoundException(query.id()));
  }
}
