package com.josh.store.Mappers;

import com.josh.store.dtos.ProductDto;
import com.josh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductDto toProductDto(Product product);

    Product createProduct(ProductDto productDto);

    void updateProduct(@MappingTarget Product product, ProductDto productDto);
}
