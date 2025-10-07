package com.store.repo;


import com.store.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findByName(String name);

    Product update(Product product);

    void deleteAll();
}
