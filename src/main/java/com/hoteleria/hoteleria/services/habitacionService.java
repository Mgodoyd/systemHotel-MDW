package com.hoteleria.hoteleria.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.habitacionDto;
import com.hoteleria.hoteleria.dtos.habitacionDto.hotelDTOW;
import com.hoteleria.hoteleria.interfaces.habitacionInterface;
import com.hoteleria.hoteleria.models.*;

@Service
public class habitacionService {

    @Autowired
    private habitacionInterface habitacionInterface;

    /**
     * Converts a habitacion entity to a habitacionDto.
     *
     * @param habitacion the habitacion entity to be converted
     * @return the converted habitacionDto
     */
    private habitacionDto convertToDto(habitación habitacion) {
        habitacionDto dto = new habitacionDto();
        dto.setId(habitacion.getId());

        hotelDTOW hotelDto = new hotelDTOW();
        hotelDto.setId(habitacion.getHotel().getId());
        dto.setHotel(hotelDto);

        dto.setNumero(habitacion.getNumero());
        dto.setTipo(habitacion.getTipo());
        dto.setDescripcion(habitacion.getDescripcion());
        dto.setImagen(habitacion.getImagen());
        dto.setPrecio(habitacion.getPrecio());
        dto.setReservaciones(habitacion.getReservaciones());
        dto.setPromociones(habitacion.getPromociones());

        return dto;
    }

    /**
     * Converts a habitacionDto object to a habitación entity.
     *
     * @param dto the habitacionDto object to be converted
     * @return the converted habitación entity
     */
    private habitación convertToEntity(habitacionDto dto) {
        return new habitación.Builder()
                .id(dto.getId())
                .hotel(new hotel.Builder().id(dto.getHotel().getId()).build())
                .numero(dto.getNumero())
                .tipo(dto.getTipo())
                .descripcion(dto.getDescripcion())
                .imagen(dto.getImagen())
                .precio(dto.getPrecio())
                .build();
    }

    /**
     * Retrieves a list of all habitacionDto objects.
     *
     * @return a list of habitacionDto objects converted from the list of habitación
     *         entities.
     */
    public List<habitacionDto> findAll() {
        List<habitación> habitaciones = habitacionInterface.findAll();
        return habitaciones.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Finds a habitacion by its numero.
     *
     * @param numero the numero of the habitacion to find
     * @return the habitacionDto corresponding to the found habitacion
     */
    public habitacionDto findByNumero(String numero) {
        Optional<habitación> habitacion = habitacionInterface.findByNumero(numero);
        if (habitacion.isPresent()) {
            return convertToDto(habitacion.get());
        }

        return null;
    }

    /**
     * Finds a habitacion by its unique identifier.
     *
     * @param id the unique identifier of the habitacion
     * @return a habitacionDto object if the habitacion is found, otherwise null
     */
    public habitacionDto findById(UUID id) {
        Optional<habitación> habitacion = habitacionInterface.findById(id);
        if (habitacion.isPresent()) {
            return convertToDto(habitacion.get());
        }

        return null;
    }

    /**
     * Saves the given habitación object using the habitacionInterface.
     *
     * @param habitación the habitación object to be saved
     */
    public habitacionDto save(habitacionDto habitacionDto) {
        habitación habitacion = convertToEntity(habitacionDto);
        habitación savedHabitacion = habitacionInterface.save(habitacion);
        return convertToDto(savedHabitacion);
    }

    /**
     * Updates an existing habitacion entity with the provided habitacionDto data.
     *
     * @param habitacionDto the data transfer object containing the updated
     *                      information
     */
    public habitacionDto update(habitacionDto habitacionDto) {
        habitación habitacion = convertToEntity(habitacionDto);
        habitación updatedHabitacion = habitacionInterface.save(habitacion);
        return convertToDto(updatedHabitacion);
    }

    public boolean deleteById(UUID id) {
        habitación habitacion = habitacionInterface.findById(id).orElse(null);
        if (habitacion == null) {
            return false;
        }
        habitacionInterface.delete(habitacion);
        return true;
    }
}