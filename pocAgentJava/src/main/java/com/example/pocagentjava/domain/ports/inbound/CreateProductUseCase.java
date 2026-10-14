package com.example.pocagentjava.domain.ports.inbound;

import com.example.pocagentjava.domain.models.product.CreateProductCommand;
import com.example.pocagentjava.domain.models.product.Product;

public interface CreateProductUseCase {

  Product create(CreateProductCommand command);
}
