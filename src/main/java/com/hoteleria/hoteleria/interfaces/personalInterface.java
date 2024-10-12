package com.hoteleria.hoteleria.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.personal;

/**
 * Repository interface for managing {@link personal} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query
 * methods.
 * 
 * Methods:
 * - {@link #findByEmail(String)}: Finds a personal entity by its email.
 * - {@link #findByPhone(String)}: Finds a personal entity by its phone number.
 * - {@link #findById(UUID)}: Finds a personal entity by its ID.
 * - {@link #findAll()}: Retrieves all personal entities.
 * 
 * @see JpaRepository
 */
@Repository
public interface personalInterface extends JpaRepository<personal, UUID> {

    Optional<personal> findByEmail(String email);

    Optional<personal> findByPhone(String phone);

    Optional<personal> findById(UUID id);

    List<personal> findAll();

}
