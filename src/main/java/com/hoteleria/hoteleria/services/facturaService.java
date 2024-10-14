package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.facturaDto;
import com.hoteleria.hoteleria.dtos.facturaDto.reservacionDTO;
import com.hoteleria.hoteleria.interfaces.facturaInterface;
import com.hoteleria.hoteleria.models.cliente;
import com.hoteleria.hoteleria.models.factura;
import com.hoteleria.hoteleria.models.habitación;
import com.hoteleria.hoteleria.models.reservacion;

import jakarta.validation.Valid;

@Service
public class facturaService {
    @Autowired
    private facturaInterface facturaInterface;

    private facturaDto convertToDto(factura factura) {
        facturaDto dto = new facturaDto();
        dto.setId(factura.getId());
        dto.setFecha_emision(factura.getFecha_emision());
        dto.setDescripcion(factura.getDescripcion());
        dto.setMonto_total(factura.getMonto_total());

        // Convertir reservacion a DTO
        reservacionDTO reservacionDto = new reservacionDTO();
        reservacionDto.setId(factura.getReservacion().getId());

        // Convertir cliente a DTO, asegurándose de que no sea null
        if (factura.getReservacion().getCliente() != null) {
            reservacionDTO.clienteDTO clienteDto = new reservacionDTO.clienteDTO();
            clienteDto.setId(factura.getReservacion().getCliente().getId());
            clienteDto.setNombre(factura.getReservacion().getCliente().getNombre());
            clienteDto.setNit(factura.getReservacion().getCliente().getNit());
            reservacionDto.setCliente(clienteDto);
        }

        // Convertir habitacion a DTO, asegurándose de que no sea null
        if (factura.getReservacion().getHabitacion() != null) {
            reservacionDTO.habitacionDTO habitacionDto = new reservacionDTO.habitacionDTO();
            habitacionDto.setId(factura.getReservacion().getHabitacion().getId());
            habitacionDto.setNumero(factura.getReservacion().getHabitacion().getNumero());
            habitacionDto.setTipo(factura.getReservacion().getHabitacion().getTipo());
            reservacionDto.setHabitacion(habitacionDto);
        }

        // Asignar la reservación al DTO de factura
        dto.setReservacion(reservacionDto);

        return dto;
    }

    // Método para convertir de DTO a entidad
    private factura convertToEntity(facturaDto dto) {
        factura factura = new factura();
        factura.setId(dto.getId());
        factura.setFecha_emision(dto.getFecha_emision());
        factura.setDescripcion(dto.getDescripcion());
        factura.setMonto_total(dto.getMonto_total());

        // Convertir de DTO a entidad Reservacion
        reservacion reservacion = new reservacion();
        reservacion.setId(dto.getReservacion().getId());

        // Convertir de DTO a entidad Cliente, solo si no es nulo
        if (dto.getReservacion().getCliente() != null) {
            cliente.Builder clienteBuilder = new cliente.Builder();
            clienteBuilder.id(dto.getReservacion().getCliente().getId());
            clienteBuilder.nombre(dto.getReservacion().getCliente().getNombre());
            clienteBuilder.nit(dto.getReservacion().getCliente().getNit());
            reservacion.setCliente(clienteBuilder.build());
        }
        // Convertir de DTO a entidad Habitación, solo si no es nulo
        if (dto.getReservacion().getHabitacion() != null) {
            habitación habitacion = new habitación();
            habitacion.setId(dto.getReservacion().getHabitacion().getId());
            habitacion.setNumero(dto.getReservacion().getHabitacion().getNumero());
            habitacion.setTipo(dto.getReservacion().getHabitacion().getTipo());
            reservacion.setHabitacion(habitacion); // Asignar habitación a la reservación
        }

        // Asignar la reservación convertida a la factura
        factura.setReservacion(reservacion);

        return factura;
    }

    public List<facturaDto> getAll() {
        List<factura> facturas = facturaInterface.findAll();
        return facturas.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public facturaDto getById(UUID id) {
        Optional<factura> facturaOpt = facturaInterface.findById(id);
        if (facturaOpt.isPresent()) {
            return convertToDto(facturaOpt.get());
        } else {
            return null;
        }
    }

    public facturaDto save(factura factura2) {
        factura facturanew = facturaInterface.save(factura2);
        return convertToDto(facturanew);
    }

    public boolean delete(UUID id) {
        facturaInterface.deleteById(id);
        return true;
    }

    public facturaDto update(facturaDto facturaDto) {
        factura factura = convertToEntity(facturaDto);
        facturaInterface.save(factura);
        return facturaDto;
    }
}
