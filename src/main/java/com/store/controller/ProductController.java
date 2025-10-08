package com.store.controller;

import com.store.model.dto.ProductRequest;
import com.store.model.dto.ProductResponse;
import com.store.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request) {
        ProductResponse response = service.addProduct(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findByName(@RequestParam(name = "name", required = false) String name) {
        if (name == null || name.isBlank()) {
            name = "";
        }
        return ResponseEntity.ok(service.findByName(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/price")
    public ResponseEntity<ProductResponse> changePrice(
            @PathVariable Long id,
            @RequestParam double price
    ) {
        return ResponseEntity.ok(service.changePrice(id, price));
    }
}