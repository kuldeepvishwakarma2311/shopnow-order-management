package com.shopnow.order.service.impl;

import com.shopnow.order.dto.ProductDTO;
import com.shopnow.order.generic.BusinessException;
import com.shopnow.order.generic.ResourceNotFoundException;
import com.shopnow.order.mapper.ProductMapper;
import com.shopnow.order.model.Product;
import com.shopnow.order.repository.ProductRepository;
import com.shopnow.order.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    @Override
    public ProductDTO createProduct(ProductDTO request) {
        productRepository.findBySku(request.getSku()).ifPresent(existing -> {
            throw new BusinessException("Product SKU already exists");
        });
        Product product = productMapper.toEntity(request);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO request) {
        Product existing = productRepository.findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.findBySku(request.getSku())
            .filter(product -> !product.getId().equals(existing.getId()))
            .ifPresent(product -> {
                throw new BusinessException("Product SKU already exists");
            });
        existing.setSku(request.getSku());
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStatus(request.getStatus());
        return productMapper.toDto(productRepository.save(existing));
    }
}
