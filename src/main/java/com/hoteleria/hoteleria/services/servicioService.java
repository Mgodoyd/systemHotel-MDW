package com.hoteleria.hoteleria.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.serviciosDto;
import com.hoteleria.hoteleria.dtos.serviciosDto.reservacionDTO;
import com.hoteleria.hoteleria.interfaces.reservacionInterface;
import com.hoteleria.hoteleria.interfaces.serviciosInterface;
import com.hoteleria.hoteleria.models.reservacion;
import com.hoteleria.hoteleria.models.servicio;

@Service
public class servicioService {
    @Autowired
    private serviciosInterface serviciosInterface;

    @Autowired
    private reservacionInterface reservacionInterface;

    /**
     * Converts a {@link servicio} entity to a {@link serviciosDto}.
     *
     * @param servicio the {@link servicio} entity to be converted
     * @return the converted {@link serviciosDto} object
     */
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

    /**
     * Converts a serviciosDto object to a servicio entity.
     *
     * @param servicioDto the DTO object containing the service details
     * @return the converted servicio entity
     * @throws IllegalArgumentException if the reservacion associated with the
     *                                  servicioDto is not found
     */
    private servicio convertToEntity(serviciosDto servicioDto) {
        reservacion reservacion = reservacionInterface.findById(servicioDto.getReservacion().getId())
                .orElseThrow(() -> new IllegalArgumentException("Reservacion not found"));

        servicio.Builder servicioBuilder = new servicio.Builder()
                .id(servicioDto.getId())
                .reservacion(reservacion)
                .nombre(servicioDto.getNombre())
                .descripcion(servicioDto.getDescripcion())
                .precio(servicioDto.getPrecio());

        servicio servicio = servicioBuilder.build();
        return servicio;
    }

    /**
     * Retrieves a list of services and converts them to DTOs.
     *
     * @return a list of {@link serviciosDto} objects representing the services.
     */
    public List<serviciosDto> getServicios() {
        List<servicio> servicios = serviciosInterface.findAll();
        return servicios.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a service by its unique identifier.
     *
     * @param id the unique identifier of the service to retrieve
     * @return a serviciosDto object representing the service if found, or null if
     *         not found
     */
    public serviciosDto getServicio(UUID id) {
        Optional<servicio> servicio = serviciosInterface.findById(id);
        if (servicio.isPresent()) {
            return convertToDTO(servicio.get());
        }
        return null;
    }

    /**
     * Retrieves a service by its name.
     *
     * @param nombre the name of the service to retrieve
     * @return a DTO representation of the service
     */
    public serviciosDto getServicioByNombre(String nombre) {
        Optional<servicio> servicio = serviciosInterface.findByNombre(nombre);
        if (servicio.isPresent()) {
            return convertToDTO(servicio.get());
        }
        return null;
    }

    /**
     * Saves a given serviciosDto object by converting it to an entity,
     * saving it using the serviciosInterface, and then converting it back to a DTO.
     *
     * @param servicioDto the serviciosDto object to be saved
     * @return the saved serviciosDto object
     */
    public serviciosDto save(serviciosDto servicioDto) {
        servicio servicio = convertToEntity(servicioDto);
        servicio = serviciosInterface.save(servicio);
        return convertToDTO(servicio);
    }

    /**
     * Updates an existing service with the provided service data transfer object
     * (DTO).
     *
     * @param servicioDto the service DTO containing updated information.
     * @return the updated service DTO.
     */
    public serviciosDto update(serviciosDto servicioDto) {
        servicio existingServicio = serviciosInterface.findById(servicioDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio not found"));

        servicio updatedServicio = new servicio.Builder()
                .id(existingServicio.getId())
                .reservacion(existingServicio.getReservacion())
                .nombre(servicioDto.getNombre())
                .descripcion(servicioDto.getDescripcion())
                .precio(servicioDto.getPrecio())
                .serviciosHabitacion(existingServicio.getServiciosHabitacion())
                .promociones(existingServicio.getPromociones())
                .parqueos(existingServicio.getParqueos())
                .usoInstalaciones(existingServicio.getUsoInstalaciones())
                .createdAt(existingServicio.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        updatedServicio = serviciosInterface.save(updatedServicio);
        return convertToDTO(updatedServicio);
    }

    /**
     * Deletes a service by its unique identifier.
     *
     * @param id the unique identifier of the service to be deleted
     * @return true if the service was successfully deleted
     */
    public boolean delete(UUID id) {
        serviciosInterface.deleteById(id);
        return true;
    }
}
