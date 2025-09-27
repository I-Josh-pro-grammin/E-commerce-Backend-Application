package com.josh.store.controllers;

import com.josh.store.Mappers.ProductMapper;
import com.josh.store.dtos.ProductDto;
import com.josh.store.dtos.UserDto;
import com.josh.store.entities.Product;
import com.josh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Filtering products with optional categoryId
    @GetMapping("")
    public List<ProductDto> allProducts(
            @RequestParam(name = "categoryId", required = false) Byte categoryId
    ) {
        List<Product> products;
        if(categoryId != null){
            products = productRepository.findByCategoryId(categoryId);
        }else {
            products = productRepository.findAllWithCategory();
        }

        return products.stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable long id) {
        var product = productRepository.findById(id).orElse(null);
        assert product != null;
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory().getId());
    }
}
