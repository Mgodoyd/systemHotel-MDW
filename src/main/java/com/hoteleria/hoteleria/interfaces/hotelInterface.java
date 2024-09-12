package com.hoteleria.hoteleria.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.hotel;

@Repository
public interface hotelInterface extends JpaRepository<hotel, UUID> {

    List<hotel> findAll();

    hotel findByName(String name);

    void deleteById(UUID id);

}
