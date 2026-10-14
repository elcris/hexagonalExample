package com.example.pocagentjava.domain.ports.inbound;

import com.example.pocagentjava.domain.models.product.Product;
import com.example.pocagentjava.domain.models.product.UpdateProductCommand;

public interface UpdateProductUseCase {

  Product update(UpdateProductCommand command);
}
