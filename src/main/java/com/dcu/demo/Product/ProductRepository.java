package com.dcu.demo.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByTitleContainingIgnoreCase(String keyword);
}
