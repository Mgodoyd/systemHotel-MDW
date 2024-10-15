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

    /**
     * Converts a {@link servicioHabitacion} entity to a
     * {@link servicioHabitacionDto}.
     *
     * @param servicioHabitacion the entity to be converted
     * @return the converted {@link servicioHabitacionDto}
     */
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

    /**
     * Converts a servicioHabitacionDto object to a servicioHabitacion entity.
     *
     * @param servicioHabitacionDto the DTO object containing the data to be
     *                              converted
     * @return a servicioHabitacion entity populated with the data from the DTO
     */
    private servicioHabitacion convertToEntity(servicioHabitacionDto servicioHabitacionDto) {
        servicioHabitacion servicioHabitacion = new servicioHabitacion();
        servicioHabitacion.setId(servicioHabitacionDto.getId());

        servicio.Builder servicioBuilder = new servicio.Builder()
                .id(servicioHabitacionDto.getServicio().getId())
                .nombre(servicioHabitacionDto.getServicio().getNombre());
        servicioHabitacion.setServicio(servicioBuilder.build());

        servicioHabitacion.setCantidad(servicioHabitacionDto.getCantidad());
        servicioHabitacion.setPrecio_total(servicioHabitacionDto.getPrecio_total());

        return servicioHabitacion;
    }

    /**
     * Retrieves a list of servicioHabitacionDto objects.
     *
     * This method fetches all servicioHabitacion entities from the database,
     * converts them to servicioHabitacionDto objects, and returns the list of DTOs.
     *
     * @return a list of servicioHabitacionDto objects
     */
    public List<servicioHabitacionDto> getServicioHabitaciones() {
        List<servicioHabitacion> servicioHabitaciones = servicioHabitacionInterface.findAll();
        return servicioHabitaciones.stream().map(this::convertToDTO).toList();
    }

    /**
     * Retrieves a servicioHabitacionDto object by its unique identifier.
     *
     * @param id the unique identifier of the servicioHabitacion
     * @return the corresponding servicioHabitacionDto object, or null if not found
     */
    public servicioHabitacionDto getServicioHabitacion(UUID id) {
        servicioHabitacion servicioHabitacion = servicioHabitacionInterface.findById(id).orElse(null);
        if (servicioHabitacion == null) {
            return null;
        }
        return convertToDTO(servicioHabitacion);
    }

    /**
     * Saves a servicioHabitacionDto object by converting it to an entity,
     * saving it using the servicioHabitacionInterface, and then converting
     * the saved entity back to a DTO.
     *
     * @param servicioHabitacionDto the DTO object to be saved
     * @return the saved servicioHabitacionDto object
     */
    public servicioHabitacionDto save(servicioHabitacionDto servicioHabitacionDto) {
        servicioHabitacion servicioHabitacion = convertToEntity(servicioHabitacionDto);
        servicioHabitacion = servicioHabitacionInterface.save(servicioHabitacion);
        return convertToDTO(servicioHabitacion);
    }

    /**
     * Deletes a service room by its unique identifier.
     *
     * @param id the unique identifier of the service room to be deleted
     * @return true if the deletion was successful
     */
    public boolean delete(UUID id) {
        servicioHabitacionInterface.deleteById(id);
        return true;
    }

    /**
     * Updates an existing servicioHabitacion entity with the provided
     * servicioHabitacionDto.
     * Converts the DTO to an entity, saves the updated entity, and then converts it
     * back to a DTO.
     *
     * @param servicioHabitacionDto the DTO containing the updated data for the
     *                              servicioHabitacion entity
     * @return the updated servicioHabitacionDto after the entity has been saved
     */
    public servicioHabitacionDto update(servicioHabitacionDto servicioHabitacionDto) {
        servicioHabitacion servicioHabitacion = convertToEntity(servicioHabitacionDto);
        servicioHabitacion = servicioHabitacionInterface.save(servicioHabitacion);
        return convertToDTO(servicioHabitacion);
    }
}
