package com.hoteleria.hoteleria.interfaces;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.habitación;

/**
 * Repository interface for managing {@link habitación} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query
 * methods.
 * 
 * @see JpaRepository
 */
@Repository
public interface habitacionInterface extends JpaRepository<habitación, UUID> {

    Optional<habitación> findByNumero(String numero);

    void deleteById(UUID id);
}
