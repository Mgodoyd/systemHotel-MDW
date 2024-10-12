package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.promocion;

/**
 * Repository interface for managing {@link promocion} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 * 
 * @see JpaRepository
 */
@Repository
public interface promocionInterface extends JpaRepository<promocion, UUID> {

    void deleteById(UUID id);

}
