package com.example.pocagentjava.infrastructure.adapters.outbound.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.infrastructure.adapters.outbound.persistence.mapper.ProductEntityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({ProductPersistenceAdapter.class, ProductEntityMapper.class})
class ProductPersistenceAdapterTest {

  @Autowired
  private ProductPersistenceAdapter adapter;

  @Test
  void savesAndLoadsProductUsingSqlSchema() {
    Product saved = adapter.save(new Product(null, "Keyboard", "Mechanical", true));

    assertThat(saved.id()).isNotNull();
    assertThat(adapter.findById(saved.id())).contains(saved);
    assertThat(adapter.findAll()).containsExactly(saved);
  }
}
