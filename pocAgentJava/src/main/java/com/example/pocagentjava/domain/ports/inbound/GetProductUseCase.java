package com.example.pocagentjava.domain.ports.inbound;

import com.example.pocagentjava.domain.models.product.GetProductQuery;
import com.example.pocagentjava.domain.models.product.Product;

public interface GetProductUseCase {

  Product get(GetProductQuery query);
}
