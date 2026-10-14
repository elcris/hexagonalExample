package com.example.pocagentjava.domain.ports.inbound;

import com.example.pocagentjava.domain.models.product.Product;
import java.util.List;

public interface ListProductsUseCase {

  List<Product> list();
}
