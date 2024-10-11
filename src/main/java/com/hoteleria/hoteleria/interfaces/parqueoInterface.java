package com.hoteleria.hoteleria.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.parqueo;

@Repository
public interface parqueoInterface extends JpaRepository<parqueo, UUID> {

    void deleteById(UUID id);

}
