package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.cliente;

@Repository
public interface clienteInterface extends JpaRepository<cliente, UUID> {

    cliente findByNit(String nit);

    void deleteById(UUID id);

}