package com.store.model.dto;

import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        double price,
        LocalDateTime createdAt
) {
}
