package com.hoteleria.hoteleria.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoteleria.hoteleria.models.personal;

@Repository
public interface personalInterface extends JpaRepository<personal, UUID> {

    Optional<personal> findByEmail(String email);

    personal findByPhone(String phone);

    Optional<personal> findById(UUID id);

    List<personal> findAll();

}
