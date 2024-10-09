package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.habitacionDto;
import com.hoteleria.hoteleria.dtos.habitacionDto.hotelDTOW;
import com.hoteleria.hoteleria.dtos.hotelDto;
import com.hoteleria.hoteleria.interfaces.habitacionInterface;
import com.hoteleria.hoteleria.models.habitación;
import com.hoteleria.hoteleria.models.hotel;

import jakarta.validation.Valid;

@Service
public class habitacionService {

    @Autowired
    private habitacionInterface habitacionInterface;

    // Método para convertir de Entidad a DTO
    private habitacionDto convertToDto(habitación habitacion) {
        habitacionDto dto = new habitacionDto();
        dto.setId(habitacion.getId());

        // Crear DTO para hotel y asignar solo el ID
        hotelDTOW hotelDto = new hotelDTOW();
        hotelDto.setId(habitacion.getHotel().getId());
        dto.setHotel(hotelDto); // Asignar el DTO del hotel a la habitación

        dto.setNumero(habitacion.getNumero());
        dto.setTipo(habitacion.getTipo());
        dto.setDescripcion(habitacion.getDescripcion());
        dto.setPrecio(habitacion.getPrecio());
        dto.setReservaciones(habitacion.getReservaciones());
        dto.setPromociones(habitacion.getPromociones());

        return dto;
    }

    // Método para convertir de DTO a Entidad
    private habitación convertToEntity(habitacionDto dto) {
        habitación habitacion = new habitación();
        habitacion.setId(dto.getId());

        // Convertir hotelDTO a hotel entidad y asignar
        hotel hotel = new hotel();
        hotel.setId(dto.getHotel().getId());
        habitacion.setHotel(hotel);

        habitacion.setNumero(dto.getNumero());
        habitacion.setTipo(dto.getTipo());
        habitacion.setDescripcion(dto.getDescripcion());
        habitacion.setPrecio(dto.getPrecio());
        habitacion.setReservaciones(dto.getReservaciones());
        habitacion.setPromociones(dto.getPromociones());

        return habitacion;
    }

    // Encontrar todas las habitaciones y convertirlas a DTO
    public List<habitacionDto> findAll() {
        List<habitación> habitaciones = habitacionInterface.findAll();
        return habitaciones.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Encontrar habitación por número y devolver DTO
    public habitacionDto findByNumero(String numero) {
        habitación habitacion = habitacionInterface.findByNumero(numero);
        return convertToDto(habitacion);
    }

    // Encontrar habitación por ID y devolver DTO
    public habitacionDto findById(UUID id) {
        habitación habitacion = habitacionInterface.findById(id).orElse(null);
        return habitacion != null ? convertToDto(habitacion) : null;
    }

    // Guardar o actualizar una habitación usando DTO
    public void save(habitación habitación) {
        habitacionInterface.save(habitación);
    }

    // Actualizar habitación usando DTO
    public void update(habitacionDto habitacionDto) {
        habitación habitacion = convertToEntity(habitacionDto);
        habitacionInterface.save(habitacion);
    }

    // Borrar habitación por ID
    public boolean deleteById(UUID id) {
        habitacionInterface.deleteById(id);
        return true;
    }
}