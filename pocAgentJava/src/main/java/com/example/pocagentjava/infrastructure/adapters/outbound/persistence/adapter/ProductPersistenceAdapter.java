package com.example.pocagentjava.infrastructure.adapters.outbound.persistence.adapter;

import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.domain.ports.outbound.ProductPersistencePort;
import com.example.pocagentjava.infrastructure.adapters.outbound.persistence.mapper.ProductEntityMapper;
import com.example.pocagentjava.infrastructure.adapters.outbound.persistence.repository.ProductJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceAdapter implements ProductPersistencePort {

  private final ProductJpaRepository repository;
  private final ProductEntityMapper mapper;

  public ProductPersistenceAdapter(ProductJpaRepository repository, ProductEntityMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Product save(Product product) {
    return mapper.toDomain(repository.save(mapper.toEntity(product)));
  }

  @Override
  public List<Product> findAll() {
    return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public Optional<Product> findById(Long id) {
    return repository.findById(id).map(mapper::toDomain);
  }

  @Override
  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}
