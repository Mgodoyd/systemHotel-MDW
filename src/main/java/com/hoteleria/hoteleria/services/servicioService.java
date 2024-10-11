package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.serviciosDto;
import com.hoteleria.hoteleria.dtos.serviciosDto.reservacionDTO;
import com.hoteleria.hoteleria.interfaces.serviciosInterface;
import com.hoteleria.hoteleria.models.reservacion;
import com.hoteleria.hoteleria.models.servicio;

@Service
public class servicioService {
    @Autowired
    private serviciosInterface serviciosInterface;

    private serviciosDto convertToDTO(servicio servicio) {
        serviciosDto servicioDto = new serviciosDto();
        servicioDto.setId(servicio.getId());

        reservacionDTO reservacionDto = new reservacionDTO();
        reservacionDto.setId(servicio.getReservacion().getId());
        reservacionDto.setFecha_reservacion(servicio.getReservacion().getFecha_reservacion());
        servicioDto.setReservacion(reservacionDto);

        servicioDto.setNombre(servicio.getNombre());
        servicioDto.setDescripcion(servicio.getDescripcion());
        servicioDto.setPrecio(servicio.getPrecio());
        return servicioDto;
    }

    private servicio convertToEntity(serviciosDto servicioDto) {
        servicio servicio = new servicio();
        servicio.setId(servicioDto.getId());

        reservacion reservacion = new reservacion();
        reservacion.setId(servicioDto.getReservacion().getId());
        reservacion.setFecha_reservacion(servicioDto.getReservacion().getFecha_reservacion());
        servicio.setReservacion(reservacion);

        servicio.setNombre(servicioDto.getNombre());
        servicio.setDescripcion(servicioDto.getDescripcion());
        servicio.setPrecio(servicioDto.getPrecio());
        return servicio;
    }

    public List<serviciosDto> getServicios() {
        List<servicio> servicios = serviciosInterface.findAll();
        return servicios.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public serviciosDto getServicio(UUID id) {
        Optional<servicio> servicio = serviciosInterface.findById(id);
        if (servicio.isPresent()) {
            return convertToDTO(servicio.get());
        }
        return null;
    }

    public serviciosDto getServicioByNombre(String nombre) {
        servicio servicio = serviciosInterface.findByNombre(nombre);
        return convertToDTO(servicio);
    }

    public serviciosDto save(serviciosDto servicioDto) {
        servicio servicio = convertToEntity(servicioDto);
        servicio = serviciosInterface.save(servicio);
        return convertToDTO(servicio);
    }

    public serviciosDto update(serviciosDto servicioDto) {
        servicio servicio = convertToEntity(servicioDto);
        servicio = serviciosInterface.save(servicio);
        return convertToDTO(servicio);
    }

    public boolean delete(UUID id) {
        serviciosInterface.deleteById(id);
        return true;
    }
}
