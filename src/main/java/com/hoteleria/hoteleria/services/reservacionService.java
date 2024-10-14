package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.reservacionDto;
import com.hoteleria.hoteleria.dtos.reservacionDto.clienteDTO;
import com.hoteleria.hoteleria.dtos.reservacionDto.habitacionDTO;
import com.hoteleria.hoteleria.interfaces.reservacionInterface;
import com.hoteleria.hoteleria.models.*;

@Service
public class reservacionService {
    @Autowired
    private reservacionInterface reservacionInterface;

    /**
     * Converts a reservacion entity to a reservacionDto.
     *
     * @param reservacion the reservacion entity to convert
     * @return the converted reservacionDto
     */
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

    /**
     * Converts a reservacionDto object to a reservacion entity.
     *
     * @param dto the reservacionDto object to be converted
     * @return the converted reservacion entity
     */
    private reservacion convertToEntity(reservacionDto dto) {
        reservacion reservacion = new reservacion();
        reservacion.setId(dto.getId());

        cliente.Builder cliente = new cliente.Builder()
                .id(dto.getCliente().getId())
                .nombre(dto.getCliente().getNombre())
                .nit(dto.getCliente().getNit());
        reservacion.setCliente(cliente.build());

        habitación habitacion = new habitación.Builder()
                .id(dto.getHabitacion().getId())
                .numero(dto.getHabitacion().getNumero())
                .tipo(dto.getHabitacion().getTipo())
                .build();
        reservacion.setHabitacion(habitacion);

        reservacion.setFecha_reservacion(dto.getFecha_reservacion());
        reservacion.setFecha_entrada(dto.getFecha_entrada());
        reservacion.setFecha_salida(dto.getFecha_salida());
        reservacion.setEstado(dto.getEstado());
        reservacion.setServicios(dto.getServicios());
        reservacion.setFacturas(dto.getFacturas());

        return reservacion;
    }

    /**
     * Retrieves all reservations and converts them to DTOs.
     *
     * @return a list of reservation DTOs.
     */
    public List<reservacionDto> getAll() {
        return reservacionInterface.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a reservation by its unique identifier.
     *
     * @param id the unique identifier of the reservation
     * @return the reservation data transfer object if found, otherwise null
     */
    public reservacionDto getById(UUID id) {
        Optional<reservacion> reservacionOpt = reservacionInterface.findById(id);
        if (reservacionOpt.isPresent()) {
            return convertToDto(reservacionOpt.get());
        } else {
            return null;
        }
    }

    /**
     * Saves a given reservacion entity and returns its corresponding DTO.
     *
     * @param reservacion the reservacion entity to be saved
     * @return the DTO representation of the saved reservacion entity
     */
    public reservacionDto save(reservacion reservacion) {
        reservacion savedReservacion = reservacionInterface.save(reservacion);
        return convertToDto(savedReservacion);
    }

    /**
     * Deletes a reservation by its unique identifier.
     *
     * @param id the unique identifier of the reservation to be deleted
     * @return true if the reservation was successfully deleted, false if the
     *         reservation does not exist
     */
    public boolean delete(UUID id) {
        if (reservacionInterface.existsById(id)) {
            reservacionInterface.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Updates an existing reservation with the provided reservation data transfer
     * object (DTO).
     *
     * @param reservacionDto the reservation DTO containing the updated information
     */
    public void update(reservacionDto reservacionDto) {
        reservacion reservacion = convertToEntity(reservacionDto);
        reservacionInterface.save(reservacion);
    }

}
