package com.hoteleria.hoteleria.interfaces;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.cliente;

/**
 * Repository interface for managing Cliente entities.
 * Extends JpaRepository to provide CRUD operations and additional query
 * methods.
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 * 
 */
@Repository
public interface clienteInterface extends JpaRepository<cliente, UUID> {

    Optional<cliente> findByNit(String nit);

    void deleteById(UUID id);

}