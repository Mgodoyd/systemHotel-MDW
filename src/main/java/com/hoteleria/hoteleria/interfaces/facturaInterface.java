package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.factura;

@Repository
public interface facturaInterface extends JpaRepository<factura, UUID> {

    void deleteById(UUID id);

}
