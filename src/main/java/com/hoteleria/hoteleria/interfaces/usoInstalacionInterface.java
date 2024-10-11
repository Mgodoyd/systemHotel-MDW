package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.usoInstalacion;

@Repository
public interface usoInstalacionInterface extends JpaRepository<usoInstalacion, UUID> {

    void deleteById(UUID id);

}
