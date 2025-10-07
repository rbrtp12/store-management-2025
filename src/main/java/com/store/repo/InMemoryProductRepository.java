package com.store.repo;

import com.store.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> store = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public Product save(Product product) {
        long id = counter.incrementAndGet();
        product.setId(id);
        store.put(id, product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Product> findByName(String name) {
        String lower = name.toLowerCase();
        return store.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    @Override
    public Product update(Product product) {
        store.put(product.getId(), product);
        return product;
    }

    @Override
    public void deleteAll() {
        store.clear();
        counter.set(0);
    }
}