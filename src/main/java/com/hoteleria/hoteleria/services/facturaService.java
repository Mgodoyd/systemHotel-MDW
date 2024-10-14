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
import com.hoteleria.hoteleria.models.*;

@Service
public class facturaService {
    @Autowired
    private facturaInterface facturaInterface;

    /**
     * Converts a {@link factura} entity to a {@link facturaDto}.
     *
     * @param factura the {@link factura} entity to convert
     * @return the converted {@link facturaDto}
     *
     *         The conversion includes:
     *         - Setting the ID, fecha_emision, descripcion, and monto_total fields.
     *         - Creating and setting a {@link reservacionDTO} with its ID.
     *         - If the reservacion has a cliente, creating and setting a
     *         {@link reservacionDTO.clienteDTO} with its ID, nombre, and nit.
     *         - If the reservacion has a habitacion, creating and setting a
     *         {@link reservacionDTO.habitacionDTO} with its ID, numero, and tipo.
     */
    private facturaDto convertToDto(factura factura) {
        facturaDto dto = new facturaDto();
        dto.setId(factura.getId());
        dto.setFecha_emision(factura.getFecha_emision());
        dto.setDescripcion(factura.getDescripcion());
        dto.setMonto_total(factura.getMonto_total());

        reservacionDTO reservacionDto = new reservacionDTO();
        reservacionDto.setId(factura.getReservacion().getId());

        if (factura.getReservacion().getCliente() != null) {
            reservacionDTO.clienteDTO clienteDto = new reservacionDTO.clienteDTO();
            clienteDto.setId(factura.getReservacion().getCliente().getId());
            clienteDto.setNombre(factura.getReservacion().getCliente().getNombre());
            clienteDto.setNit(factura.getReservacion().getCliente().getNit());
            reservacionDto.setCliente(clienteDto);
        }

        if (factura.getReservacion().getHabitacion() != null) {
            reservacionDTO.habitacionDTO habitacionDto = new reservacionDTO.habitacionDTO();
            habitacionDto.setId(factura.getReservacion().getHabitacion().getId());
            habitacionDto.setNumero(factura.getReservacion().getHabitacion().getNumero());
            habitacionDto.setTipo(factura.getReservacion().getHabitacion().getTipo());
            reservacionDto.setHabitacion(habitacionDto);
        }

        dto.setReservacion(reservacionDto);

        return dto;
    }

    /**
     * Converts a facturaDto object to a factura entity.
     *
     * @param dto the facturaDto object to be converted
     * @return the converted factura entity
     *
     *         The method performs the following steps:
     *         1. Creates a new factura object and sets its properties from the dto.
     *         2. Creates a new reservacion object and sets its id from the dto.
     *         3. If the reservacion's cliente is not null, it creates a new cliente
     *         using the Builder pattern and sets its properties from the dto.
     *         4. If the reservacion's habitacion is not null, it creates a new
     *         habitación object and sets its properties from the dto.
     *         5. Sets the reservacion object to the factura.
     */
    private factura convertToEntity(facturaDto dto) {
        factura factura = new factura();
        factura.setId(dto.getId());
        factura.setFecha_emision(dto.getFecha_emision());
        factura.setDescripcion(dto.getDescripcion());
        factura.setMonto_total(dto.getMonto_total());

        reservacion reservacion = new reservacion();
        reservacion.setId(dto.getReservacion().getId());

        if (dto.getReservacion().getCliente() != null) {
            cliente.Builder clienteBuilder = new cliente.Builder();
            clienteBuilder.id(dto.getReservacion().getCliente().getId());
            clienteBuilder.nombre(dto.getReservacion().getCliente().getNombre());
            clienteBuilder.nit(dto.getReservacion().getCliente().getNit());
            reservacion.setCliente(clienteBuilder.build());
        }
        if (dto.getReservacion().getHabitacion() != null) {
            habitación habitacion = new habitación();
            habitacion.setId(dto.getReservacion().getHabitacion().getId());
            habitacion.setNumero(dto.getReservacion().getHabitacion().getNumero());
            habitacion.setTipo(dto.getReservacion().getHabitacion().getTipo());
            reservacion.setHabitacion(habitacion);
        }

        factura.setReservacion(reservacion);

        return factura;
    }

    /**
     * Retrieves all factura entities, converts them to facturaDto objects, and
     * returns them as a list.
     *
     * @return a list of facturaDto objects representing all factura entities.
     */
    public List<facturaDto> getAll() {
        List<factura> facturas = facturaInterface.findAll();
        return facturas.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a facturaDto by its unique identifier.
     *
     * @param id the unique identifier of the factura to retrieve
     * @return the facturaDto if found, or null if not found
     */
    public facturaDto getById(UUID id) {
        Optional<factura> facturaOpt = facturaInterface.findById(id);
        if (facturaOpt.isPresent()) {
            return convertToDto(facturaOpt.get());
        } else {
            return null;
        }
    }

    /**
     * Saves the given factura entity and converts it to a facturaDto.
     *
     * @param factura2 the factura entity to be saved
     * @return the saved factura entity converted to a facturaDto
     */
    public facturaDto save(factura factura2) {
        factura facturanew = facturaInterface.save(factura2);
        return convertToDto(facturanew);
    }

    /**
     * Deletes a factura by its unique identifier.
     *
     * @param id the unique identifier of the factura to be deleted
     * @return true if the deletion was successful
     */
    public boolean delete(UUID id) {
        facturaInterface.deleteById(id);
        return true;
    }

    /**
     * Updates an existing factura record.
     *
     * @param facturaDto the data transfer object containing the updated details of
     *                   the factura
     * @return the updated factura data transfer object
     */
    public facturaDto update(facturaDto facturaDto) {
        factura factura = convertToEntity(facturaDto);
        facturaInterface.save(factura);
        return facturaDto;
    }
}
