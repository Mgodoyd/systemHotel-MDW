package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.hotelDto;
import com.hoteleria.hoteleria.interfaces.*;
import com.hoteleria.hoteleria.models.*;

import jakarta.transaction.Transactional;

@Service
public class hotelService {

    @Autowired
    private hotelInterface hotelinterface;

    /**
     * Retrieves a list of all hotels.
     *
     * @return a list of hotelDto objects representing all hotels.
     */
    public List<hotelDto> findAll() {
        return hotelinterface.findAll().stream()
                .map(hotel -> convertToDto(hotel, true))
                .collect(Collectors.toList());
    }

    /**
     * Finds a hotel by its name.
     *
     * @param nombre the name of the hotel to find
     * @return a hotelDto object representing the hotel with the specified name
     */
    public hotelDto findByNombre(String nombre) {
        hotel hotel = hotelinterface.findByName(nombre);
        return convertToDto(hotel, true);
    }

    /**
     * Saves the given hotel entity.
     *
     * @param hotel the hotel entity to be saved
     * @return the saved hotel entity
     */
    public hotel save(hotel hotel) {
        return hotelinterface.save(hotel);
    }

    /**
     * Deletes a hotel entity by its unique identifier.
     *
     * @param id the unique identifier of the hotel entity to be deleted
     */
    @Transactional
    public boolean deleteById(UUID id) {
        Optional<hotel> hotelOptional = hotelinterface.findById(id);

        if (hotelOptional.isPresent()) {
            hotelinterface.deleteById(id);
            return true;
        }

        return false;
    }

    /**
     * Finds a hotel by its unique identifier.
     *
     * @param id the unique identifier of the hotel
     * @return a hotelDto object representing the hotel, or null if not found
     */
    public hotelDto findById(UUID id) {
        Optional<hotel> hotel = hotelinterface.findById(id);
        if (hotel.isPresent()) {
            return convertToDto(hotel.get(), true);
        }
        return null;
    }

    /**
     * Updates an existing hotel record.
     *
     * @param hotelDto the data transfer object containing the updated hotel
     *                 information
     * @return the updated hotel data transfer object
     */
    public hotelDto update(hotelDto hotelDto) {
        hotel hotel = convertToEntity(hotelDto);
        return convertToDto(hotelinterface.save(hotel), true);
    }

    /**
     * Converts a hotelDto object to a hotel entity.
     *
     * @param hotelDto the data transfer object containing hotel information
     * @return a hotel entity constructed from the provided hotelDto
     */
    private hotel convertToEntity(hotelDto hotelDto) {
        return new hotel.Builder()
                .id(hotelDto.getId())
                .name(hotelDto.getName())
                .address(hotelDto.getAddress())
                .phone(hotelDto.getPhone())
                .email(hotelDto.getEmail())
                .description(hotelDto.getDescription())
                .rooms(hotelDto.getRooms())
                .build();
    }

    /**
     * Converts a hotel entity to a hotelDto.
     *
     * @param hotel        the hotel entity to convert
     * @param includeStaff a boolean indicating whether to include staff information
     *                     in the DTO
     * @return the converted hotelDto, or null if the input hotel is null
     */
    private hotelDto convertToDto(hotel hotel, boolean includeStaff) {
        if (hotel == null) {
            return null;
        }
        hotelDto hotelDto = new hotelDto();
        hotelDto.setId(hotel.getId());
        hotelDto.setName(hotel.getName());
        hotelDto.setAddress(hotel.getAddress());
        hotelDto.setPhone(hotel.getPhone());
        hotelDto.setEmail(hotel.getEmail());
        hotelDto.setDescription(hotel.getDescription());
        hotelDto.setRooms(hotel.getRooms());

        if (includeStaff) {
            hotelDto.setStaff(hotel.getStaff());
        }

        return hotelDto;
    }
}