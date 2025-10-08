package com.store.service;

import com.store.model.Product;
import com.store.model.dto.ProductRequest;
import com.store.model.dto.ProductResponse;
import com.store.repo.ProductRepository;
import com.store.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse addProduct(ProductRequest request) {
        if (request.price() < 0) {
            throw new IllegalArgumentException("Price must be >= 0");
        }

        Product product = new Product(
                null,
                request.name(),
                request.description(),
                request.price(),
                LocalDateTime.now()
        );

        Product saved = repository.save(product);
        log.info("Product added, id={}", saved.getId());
        return toResponse(saved);
    }

    public ProductResponse findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return toResponse(product);
    }

    public List<ProductResponse> findByName(String name) {
        return repository.findByName(name).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse changePrice(Long id, double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("New price must be >= 0");
        }

        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        product.setPrice(newPrice);
        repository.update(product);
        log.info("Price updated for id={} -> {}", id, newPrice);

        return toResponse(product);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCreatedAt()
        );
    }
}
