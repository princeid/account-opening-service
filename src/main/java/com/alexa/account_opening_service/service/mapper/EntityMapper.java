package com.alexa.account_opening_service.service.mapper;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    D toResponse(E entity);
}
