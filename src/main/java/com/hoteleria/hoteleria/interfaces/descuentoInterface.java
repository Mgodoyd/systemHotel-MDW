package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.descuentos;

@Repository
public interface descuentoInterface extends JpaRepository<descuentos, UUID> {

    void deleteById(UUID id);

    descuentos findByCodigo(String codigo);

}
