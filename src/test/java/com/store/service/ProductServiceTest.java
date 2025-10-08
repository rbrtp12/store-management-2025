package com.store.service;

import com.store.model.Product;
import com.store.model.dto.ProductRequest;
import com.store.model.dto.ProductResponse;
import com.store.repo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void testAddProduct_ShouldSaveAndReturnResponse() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1200.0);

        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductRequest request = new ProductRequest("Laptop", "laptop", 1200);
        ProductResponse response = productService.addProduct(request);

        assertNotNull(response);
        assertEquals("Laptop", response.name());
        assertEquals(1200.0, response.price());
    }

    @Test
    void testFindProduct_ShouldReturnResponseIfFound() {
        Product product = new Product();
        product.setId(2L);
        product.setName("Mouse");
        product.setPrice(25.5);

        when(productRepository.findById(2L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.findById(2L);

        assertNotNull(response);
        assertEquals(2L, response.id());
        assertEquals("Mouse", response.name());
    }

    @Test
    void testChangePrice_ShouldUpdateProductPrice() {
        Product product = new Product();
        product.setId(3L);
        product.setName("Keyboard");
        product.setPrice(45.0);

        when(productRepository.findById(3L)).thenReturn(Optional.of(product));
        when(productRepository.update(any(Product.class))).thenReturn(product);

        ProductResponse updatedResponse = productService.changePrice(3L, 60.0);

        assertNotNull(updatedResponse);
        assertEquals(60.0, updatedResponse.price());
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testFindProduct_ShouldThrowExceptionIfNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.findById(99L));

        assertEquals("Product not found: 99", exception.getMessage());
    }
}
