package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.parqueo;

/**
 * Repository interface for managing {@link parqueo} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 * 
 * @see JpaRepository
 */
@Repository
public interface parqueoInterface extends JpaRepository<parqueo, UUID> {

    void deleteById(UUID id);

}
