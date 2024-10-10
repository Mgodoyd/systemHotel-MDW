package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.reservacion;

@Repository
public interface reservacionInterface extends JpaRepository<reservacion, UUID> {

    void deleteById(UUID id);

}
