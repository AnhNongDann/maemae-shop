package com.example.maemaeshop.maemaeshop.repositories;

import com.example.maemaeshop.maemaeshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,Long> {
}
