package com.josh.store.Mappers;

import com.josh.store.dtos.ProductDto;
import com.josh.store.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    public ProductDto toProductDto(Product product);
}
