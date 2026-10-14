package com.example.pocagentjava.application.service.product;

import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.domain.ports.inbound.ListProductsUseCase;
import com.example.pocagentjava.domain.ports.outbound.ProductPersistencePort;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListProductsService implements ListProductsUseCase {

  private final ProductPersistencePort productPersistencePort;

  public ListProductsService(ProductPersistencePort productPersistencePort) {
    this.productPersistencePort = productPersistencePort;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Product> list() {
    return productPersistencePort.findAll();
  }
}
