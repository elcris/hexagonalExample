package com.example.pocagentjava.infrastructure.adapters.outbound.persistence.repository;

import com.example.pocagentjava.infrastructure.adapters.outbound.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
