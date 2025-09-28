package com.josh.store.controllers;

import com.josh.store.Mappers.ProductMapper;
import com.josh.store.Mappers.UserMapper;
import com.josh.store.dtos.ProductDto;
import com.josh.store.dtos.UserDto;
import com.josh.store.entities.Product;
import com.josh.store.repositories.ProductRepository;
import com.josh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productData,
            UriComponentsBuilder uriBuilder
    ) {
        var product = productMapper.createProduct(productData);
        productRepository.save(product);

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(productData);
    }
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

        return new ProductDto(product.getName(), product.getDescription(), product.getPrice(), product.getCategory().getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestBody ProductDto productUpdates,
            @PathVariable(name = "id") long id
    ) {
        var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        productMapper.updateProduct(product, productUpdates);
        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toProductDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable(name = "id") long id) {
        var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        productRepository.delete(product);

        return ResponseEntity.noContent().build();
    }

}
