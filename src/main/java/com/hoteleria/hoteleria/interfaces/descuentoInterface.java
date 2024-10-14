package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.descuentos;

/**
 * Repository interface for managing {@link descuentos} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 * 
 * This interface includes custom methods for deleting by ID and finding by
 * code.
 * 
 * @see JpaRepository
 */
@Repository
public interface descuentoInterface extends JpaRepository<descuentos, UUID> {

    void deleteById(UUID id);

    descuentos findByCodigo(String codigo);
}
