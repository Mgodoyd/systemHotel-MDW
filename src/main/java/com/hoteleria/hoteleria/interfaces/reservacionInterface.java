package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.reservacion;

/**
 * Repository interface for managing Reservacion entities.
 * Extends JpaRepository to provide CRUD operations and additional query
 * methods.
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 * 
 */
@Repository
public interface reservacionInterface extends JpaRepository<reservacion, UUID> {

    void deleteById(UUID id);

    void deleteByHabitacionId(UUID id);

}
