package br.com.lucasgomes.place_service.api;

import java.time.LocalDateTime;

public record PlaceResponse(Long id, String name, String slug, String state, LocalDateTime createdAt, LocalDateTime updatedAt
) {
}
