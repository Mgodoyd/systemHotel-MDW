package com.hoteleria.hoteleria.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.puesto;

/**
 * Repository interface for managing {@link puesto} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query
 * methods.
 * 
 * Methods:
 * - {@link #findByName(String)}: Finds a puesto by its name.
 * - {@link #findById(UUID)}: Finds a puesto by its ID.
 * - {@link #findAll()}: Retrieves all puestos.
 * - {@link #deleteById(UUID)}: Deletes a puesto by its ID.
 * 
 * @see JpaRepository
 * @see puesto
 */
@Repository
public interface puestoInterface extends JpaRepository<puesto, UUID> {

    puesto findByName(String name);

    Optional<puesto> findById(UUID id);

    List<puesto> findAll();

    void deleteById(UUID id);

}