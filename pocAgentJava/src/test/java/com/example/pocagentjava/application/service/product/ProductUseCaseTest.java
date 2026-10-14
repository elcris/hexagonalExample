package com.example.pocagentjava.application.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.pocagentjava.domain.exceptions.ProductNotFoundException;
import com.example.pocagentjava.domain.models.product.CreateProductCommand;
import com.example.pocagentjava.domain.models.product.GetProductQuery;
import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.domain.models.product.UpdateProductCommand;
import com.example.pocagentjava.domain.ports.outbound.ProductPersistencePort;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ProductUseCaseTest {

  private final ProductPersistencePort persistencePort = mock(ProductPersistencePort.class);

  @Test
  void createsProductThroughPersistencePort() {
    CreateProductService service = new CreateProductService(persistencePort);
    Product saved = new Product(1L, "Keyboard", "Mechanical", true);
    when(persistencePort.save(new Product(null, "Keyboard", "Mechanical", true)))
        .thenReturn(saved);

    Product result = service.create(new CreateProductCommand("Keyboard", "Mechanical", true));

    assertThat(result).isEqualTo(saved);
  }

  @Test
  void returnsProductWhenItExists() {
    GetProductService service = new GetProductService(persistencePort);
    Product product = new Product(1L, "Keyboard", "Mechanical", true);
    when(persistencePort.findById(1L)).thenReturn(Optional.of(product));

    assertThat(service.get(new GetProductQuery(1L))).isEqualTo(product);
  }

  @Test
  void rejectsUpdateWhenProductDoesNotExist() {
    UpdateProductService service = new UpdateProductService(persistencePort);
    when(persistencePort.findById(9L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.update(
        new UpdateProductCommand(9L, "Mouse", "Wireless", true)))
        .isInstanceOf(ProductNotFoundException.class);
  }

  @Test
  void rejectsDeleteWhenProductDoesNotExist() {
    DeleteProductService service = new DeleteProductService(persistencePort);
    when(persistencePort.findById(9L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.delete(9L))
        .isInstanceOf(ProductNotFoundException.class);
    verify(persistencePort, org.mockito.Mockito.never()).deleteById(9L);
  }
}
