package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.*;

/**
 * Repository interface for managing {@link servicioHabitacion} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query
 * methods.
 * 
 * @see JpaRepository
 */
@Repository
public interface servicioHabitacionInterface extends JpaRepository<servicioHabitacion, UUID> {

    void deleteById(UUID id);

}
