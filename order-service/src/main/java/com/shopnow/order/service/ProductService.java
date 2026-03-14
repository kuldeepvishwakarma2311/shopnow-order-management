package com.shopnow.order.service;

import com.shopnow.order.dto.ProductDTO;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts();
    ProductDTO createProduct(ProductDTO request);
    ProductDTO updateProduct(ProductDTO request);
}
