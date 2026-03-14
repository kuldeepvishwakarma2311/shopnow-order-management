package com.shopnow.order.controller.impl;

import com.shopnow.order.controller.ProductController;
import com.shopnow.order.dto.ProductDTO;
import com.shopnow.order.service.ProductService;
import com.shopnow.order.wrapper.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProducts() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Products fetched", productService.getProducts()));
    }

    @Override
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(ProductDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Product created", productService.createProduct(request)));
    }

    @Override
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(ProductDTO request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Product updated", productService.updateProduct(request)));
    }
}
