package com.store.model.dto;

public record ProductRequest(
        String name,
        String description,
        double price
) {
}
