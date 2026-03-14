package com.shopnow.order.controller;

import com.shopnow.order.annotation.StandardApiResponses;
import com.shopnow.order.dto.ProductDTO;
import com.shopnow.order.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Products")
@StandardApiResponses
@RequestMapping("/products")
public interface ProductController {

    @GetMapping
    ResponseEntity<ApiResponse<List<ProductDTO>>> getProducts();

    @PostMapping
    ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO request);

    @PutMapping
    ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@Valid @RequestBody ProductDTO request);
}
