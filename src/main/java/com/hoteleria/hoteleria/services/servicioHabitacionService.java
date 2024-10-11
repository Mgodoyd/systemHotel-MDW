package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.servicioHabitacionDto;
import com.hoteleria.hoteleria.interfaces.servicioHabitacionInterface;
import com.hoteleria.hoteleria.models.servicio;
import com.hoteleria.hoteleria.models.servicioHabitacion;

@Service
public class servicioHabitacionService {
    @Autowired
    private servicioHabitacionInterface servicioHabitacionInterface;

    private servicioHabitacionDto convertToDTO(servicioHabitacion servicioHabitacion) {
        servicioHabitacionDto servicioHabitacionDto = new servicioHabitacionDto();
        servicioHabitacionDto.setId(servicioHabitacion.getId());

        servicioHabitacionDto.servicioDTO servicioDto = new servicioHabitacionDto.servicioDTO();

        servicioDto.setId(servicioHabitacion.getServicio().getId());
        servicioDto.setNombre(servicioHabitacion.getServicio().getNombre());
        servicioHabitacionDto.setServicio(servicioDto);

        servicioHabitacionDto.setCantidad(servicioHabitacion.getCantidad());
        servicioHabitacionDto.setPrecio_total(servicioHabitacion.getPrecio_total());

        return servicioHabitacionDto;
    }

    private servicioHabitacion convertToEntity(servicioHabitacionDto servicioHabitacionDto) {
        servicioHabitacion servicioHabitacion = new servicioHabitacion();
        servicioHabitacion.setId(servicioHabitacionDto.getId());

        servicio servicio = new servicio();
        servicio.setId(servicioHabitacionDto.getServicio().getId());
        servicio.setNombre(servicioHabitacionDto.getServicio().getNombre());
        servicioHabitacion.setServicio(servicio);

        servicioHabitacion.setCantidad(servicioHabitacionDto.getCantidad());
        servicioHabitacion.setPrecio_total(servicioHabitacionDto.getPrecio_total());

        return servicioHabitacion;
    }

    public List<servicioHabitacionDto> getServicioHabitaciones() {
        List<servicioHabitacion> servicioHabitaciones = servicioHabitacionInterface.findAll();
        return servicioHabitaciones.stream().map(this::convertToDTO).toList();
    }

    public servicioHabitacionDto getServicioHabitacion(UUID id) {
        servicioHabitacion servicioHabitacion = servicioHabitacionInterface.findById(id).orElse(null);
        if (servicioHabitacion == null) {
            return null;
        }
        return convertToDTO(servicioHabitacion);
    }

    public servicioHabitacionDto save(servicioHabitacionDto servicioHabitacionDto) {
        servicioHabitacion servicioHabitacion = convertToEntity(servicioHabitacionDto);
        servicioHabitacion = servicioHabitacionInterface.save(servicioHabitacion);
        return convertToDTO(servicioHabitacion);
    }

    public boolean delete(UUID id) {
        servicioHabitacionInterface.deleteById(id);
        return true;
    }

    public servicioHabitacionDto update(servicioHabitacionDto servicioHabitacionDto) {
        servicioHabitacion servicioHabitacion = convertToEntity(servicioHabitacionDto);
        servicioHabitacion = servicioHabitacionInterface.save(servicioHabitacion);
        return convertToDTO(servicioHabitacion);
    }
}
