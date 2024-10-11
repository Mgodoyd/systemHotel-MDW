package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.servicio;

@Repository
public interface serviciosInterface extends JpaRepository<servicio, UUID> {

    void deleteById(UUID id);

    servicio findByNombre(String nombre);

}
