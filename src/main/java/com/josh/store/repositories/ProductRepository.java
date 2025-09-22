package com.josh.store.repositories;

import com.josh.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Byte category_id);

    List<Product> findByName(String name);
}