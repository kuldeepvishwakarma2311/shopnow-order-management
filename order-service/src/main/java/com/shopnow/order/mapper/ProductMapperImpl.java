package com.shopnow.order.mapper;

import com.shopnow.order.dto.ProductDTO;
import com.shopnow.order.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDto(Product entity) {
        if (entity == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setSku(entity.getSku());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        return Product.builder()
            .id(dto.getId())
            .sku(dto.getSku())
            .name(dto.getName())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .status(dto.getStatus())
            .build();
    }
}
