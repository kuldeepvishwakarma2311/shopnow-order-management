package com.shopnow.order.mapper;

import com.shopnow.order.dto.ProductDTO;
import com.shopnow.order.model.Product;
public interface ProductMapper {
    ProductDTO toDto(Product entity);
    Product toEntity(ProductDTO dto);
}
