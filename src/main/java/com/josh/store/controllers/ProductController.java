package com.josh.store.controllers;

import com.josh.store.Mappers.ProductMapper;
import com.josh.store.dtos.ProductDto;
import com.josh.store.entities.Product;
import com.josh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping("")
    public Iterable<ProductDto> allProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable long id) {
        return productRepository.findById(id).orElse(null);
    }
}
