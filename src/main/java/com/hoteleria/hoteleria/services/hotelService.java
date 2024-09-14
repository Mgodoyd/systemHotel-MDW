package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.hotelDto;
import com.hoteleria.hoteleria.interfaces.hotelInterface;
import com.hoteleria.hoteleria.models.hotel;

@Service
public class hotelService {

    @Autowired
    private hotelInterface hotelinterface;

    public List<hotelDto> findAll() {
        return hotelinterface.findAll().stream()
                .map(hotel -> convertToDto(hotel, true)) // Incluimos staff al listar todos los hoteles
                .collect(Collectors.toList());
    }

    public hotelDto findByNombre(String nombre) {
        hotel hotel = hotelinterface.findByName(nombre);
        return convertToDto(hotel, true);
    }

    public hotel save(hotel hotel) {
        return hotelinterface.save(hotel);
    }

    public void deleteById(UUID id) {
        hotelinterface.deleteById(id);
    }

    public hotelDto findById(UUID id) {
        hotel hotel = hotelinterface.findById(id).orElse(null);
        return convertToDto(hotel, true); // Pasamos un flag para excluir el staff
    }

    public hotelDto update(hotelDto hotelDto) {
        hotel hotel = convertToEntity(hotelDto);
        return convertToDto(hotelinterface.save(hotel), true);
    }

    private hotel convertToEntity(hotelDto hotelDto) {

        hotel hotel = new hotel();
        hotel.setId(hotelDto.getId());
        hotel.setName(hotelDto.getName());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setPhone(hotelDto.getPhone());
        hotel.setEmail(hotelDto.getEmail());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setStaff(hotelDto.getStaff());
        hotel.setRooms(hotelDto.getRooms());
        return hotel;
    }

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