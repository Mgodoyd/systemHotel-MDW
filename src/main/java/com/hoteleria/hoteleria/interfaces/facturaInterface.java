package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.factura;

/**
 * Repository interface for managing {@link factura} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query
 * methods.
 * 
 * @see JpaRepository
 * 
 */
@Repository
public interface facturaInterface extends JpaRepository<factura, UUID> {

    void deleteById(UUID id);
}
