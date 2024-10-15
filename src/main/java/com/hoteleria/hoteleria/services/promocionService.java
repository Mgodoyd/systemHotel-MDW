package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.promocionDto;
import com.hoteleria.hoteleria.dtos.promocionDto.habitacionDto;
import com.hoteleria.hoteleria.dtos.promocionDto.servicioDto;
import com.hoteleria.hoteleria.interfaces.promocionInterface;
import com.hoteleria.hoteleria.models.*;

@Service
public class promocionService {
    @Autowired
    private promocionInterface promocionInterface;

    private promocionDto convertToDTO(promocion promocion) {
        promocionDto promocionDto = new promocionDto();
        promocionDto.setId(promocion.getId());

        if (promocion.getHabitacion() != null) {
            habitacionDto habitacionDto = new habitacionDto();
            habitacionDto.setId(promocion.getHabitacion().getId());
            habitacionDto.setTipo(promocion.getHabitacion().getTipo());
            habitacionDto.setPrecio(promocion.getHabitacion().getPrecio());
            promocionDto.setHabitacion(habitacionDto);
        }

        if (promocion.getServicio() != null) {
            servicioDto servicioDto = new servicioDto();
            servicioDto.setId(promocion.getServicio().getId());
            servicioDto.setNombre(promocion.getServicio().getNombre());
            promocionDto.setServicio(servicioDto);
        }

        promocionDto.setDescripcion(promocion.getDescripcion());
        promocionDto.setTipo_servicio(promocion.getTipo_servicio());
        return promocionDto;
    }

    private promocion convertToEntity(promocionDto promocionDto) {
        promocion promocion = new promocion();
        promocion.setId(promocionDto.getId());

        habitación habitacion = new habitación();
        habitacion.setId(promocionDto.getHabitacion().getId());
        habitacion.setTipo(promocionDto.getHabitacion().getTipo());
        habitacion.setPrecio(promocionDto.getHabitacion().getPrecio());
        promocion.setHabitacion(habitacion);

        servicio.Builder servicioBuilder = new servicio.Builder()
                .id(promocionDto.getServicio().getId())
                .nombre(promocionDto.getServicio().getNombre());
        promocion.setServicio(servicioBuilder.build());

        promocion.setDescripcion(promocionDto.getDescripcion());
        promocion.setTipo_servicio(promocionDto.getTipo_servicio());
        return promocion;
    }

    public List<promocionDto> getPromociones() {
        return promocionInterface.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public promocionDto getPromocion(UUID id) {
        Optional<promocion> promocion = promocionInterface.findById(id);
        if (promocion.isPresent()) {
            return convertToDTO(promocion.get());
        }
        return null;
    }

    public promocionDto save(promocionDto promocionDto) {
        promocion promocion = convertToEntity(promocionDto);
        promocion = promocionInterface.save(promocion);
        return convertToDTO(promocion);
    }

    public promocionDto update(promocionDto promocionDto) {
        promocion promocion = convertToEntity(promocionDto);
        promocion = promocionInterface.save(promocion);
        return convertToDTO(promocion);
    }

    public boolean deletePromocion(UUID id) {
        promocionInterface.deleteById(id);
        return true;
    }

}
