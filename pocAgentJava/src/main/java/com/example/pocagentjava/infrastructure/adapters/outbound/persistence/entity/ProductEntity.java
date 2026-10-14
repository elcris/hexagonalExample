package com.example.pocagentjava.infrastructure.adapters.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120)
  private String name;

  @Column(nullable = false, length = 1000)
  private String description;

  @Column(nullable = false)
  private boolean active;

  protected ProductEntity() {
  }

  public ProductEntity(Long id, String name, String description, boolean active) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.active = active;
  }

  public ProductEntity(String name, String description, boolean active) {
    this(null, name, description, active);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean isActive() {
    return active;
  }
}
