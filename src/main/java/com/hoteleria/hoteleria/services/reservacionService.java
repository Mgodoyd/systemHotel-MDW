package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.clienteDto;
import com.hoteleria.hoteleria.dtos.habitacionDto;
import com.hoteleria.hoteleria.dtos.reservacionDto;
import com.hoteleria.hoteleria.dtos.reservacionDto.clienteDTO;
import com.hoteleria.hoteleria.dtos.reservacionDto.habitacionDTO;
import com.hoteleria.hoteleria.interfaces.reservacionInterface;
import com.hoteleria.hoteleria.models.cliente;
import com.hoteleria.hoteleria.models.habitación;
import com.hoteleria.hoteleria.models.reservacion;

@Service
public class reservacionService {
    @Autowired
    private reservacionInterface reservacionInterface;

    // Método para convertir de entidad a DTO
    private reservacionDto convertToDto(reservacion reservacion) {
        reservacionDto dto = new reservacionDto();
        dto.setId(reservacion.getId());

        clienteDTO clienteDto = new clienteDTO();
        clienteDto.setId(reservacion.getCliente().getId());
        clienteDto.setNombre(reservacion.getCliente().getNombre());
        clienteDto.setNit(reservacion.getCliente().getNit());
        dto.setCliente(clienteDto);

        habitacionDTO habitacionDto = new habitacionDTO();
        habitacionDto.setId(reservacion.getHabitacion().getId());
        habitacionDto.setNumero(reservacion.getHabitacion().getNumero());
        habitacionDto.setTipo(reservacion.getHabitacion().getTipo());
        dto.setHabitacion(habitacionDto);

        dto.setFecha_reservacion(reservacion.getFecha_reservacion());
        dto.setFecha_entrada(reservacion.getFecha_entrada());
        dto.setFecha_salida(reservacion.getFecha_salida());
        dto.setEstado(reservacion.getEstado());
        dto.setServicios(reservacion.getServicios());
        dto.setFacturas(reservacion.getFacturas());

        return dto;
    }

    // Método para convertir de DTO a entidad
    private reservacion convertToEntity(reservacionDto dto) {
        reservacion reservacion = new reservacion();
        reservacion.setId(dto.getId());

        cliente cliente = new cliente();
        cliente.setId(dto.getCliente().getId());
        cliente.setNombre(dto.getCliente().getNombre());
        cliente.setNit(dto.getCliente().getNit());
        reservacion.setCliente(cliente);

        habitación habitacion = new habitación();
        habitacion.setId(dto.getHabitacion().getId());
        habitacion.setNumero(dto.getHabitacion().getNumero());
        habitacion.setTipo(dto.getHabitacion().getTipo());
        reservacion.setHabitacion(habitacion);

        reservacion.setFecha_reservacion(dto.getFecha_reservacion());
        reservacion.setFecha_entrada(dto.getFecha_entrada());
        reservacion.setFecha_salida(dto.getFecha_salida());
        reservacion.setEstado(dto.getEstado());
        reservacion.setServicios(dto.getServicios());
        reservacion.setFacturas(dto.getFacturas());

        return reservacion;
    }

    public List<reservacionDto> getAll() {
        return reservacionInterface.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public reservacionDto getById(UUID id) {
        Optional<reservacion> reservacionOpt = reservacionInterface.findById(id);
        if (reservacionOpt.isPresent()) {
            return convertToDto(reservacionOpt.get());
        } else {
            return null;
        }
    }

    public reservacionDto save(reservacion reservacion) {
        reservacion savedReservacion = reservacionInterface.save(reservacion);
        return convertToDto(savedReservacion);
    }

    public boolean delete(UUID id) {
        if (reservacionInterface.existsById(id)) {
            reservacionInterface.deleteById(id);
            return true;
        }
        return false;
    }

    public void update(reservacionDto reservacionDto) {
        reservacion reservacion = convertToEntity(reservacionDto);
        reservacionInterface.save(reservacion);
    }

}
