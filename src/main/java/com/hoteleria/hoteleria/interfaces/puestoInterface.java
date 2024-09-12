package com.hoteleria.hoteleria.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoteleria.hoteleria.models.puesto;

public interface puestoInterface extends JpaRepository<puesto, UUID> {

    puesto findByName(String name);

    Optional<puesto> findById(UUID id);

    List<puesto> findAll();

    void deleteById(UUID id);

}