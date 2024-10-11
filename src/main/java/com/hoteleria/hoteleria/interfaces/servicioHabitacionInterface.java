package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.servicioHabitacion;

@Repository
public interface servicioHabitacionInterface extends JpaRepository<servicioHabitacion, UUID> {

    void deleteById(UUID id);

}
