package com.example.pocagentjava.domain.ports.outbound;

import com.example.pocagentjava.domain.models.product.Product;
import java.util.List;
import java.util.Optional;

public interface ProductPersistencePort {

  Product save(Product product);

  List<Product> findAll();

  Optional<Product> findById(Long id);

  void deleteById(Long id);
}
