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

    /**
     * Converts a promocion entity to a promocionDto.
     *
     * @param promocion the promocion entity to be converted
     * @return a promocionDto containing the data from the promocion entity
     */
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

    /**
     * Converts a promocionDto object to a promocion entity.
     *
     * @param promocionDto the DTO object containing the data to be converted
     * @return a promocion entity populated with the data from the DTO
     */
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

    /**
     * Retrieves a list of all promotions.
     *
     * This method fetches all promotion entities from the data source,
     * converts them to DTOs (Data Transfer Objects), and returns them
     * as a list.
     *
     * @return a list of {@link promocionDto} objects representing the promotions.
     */
    public List<promocionDto> getPromociones() {
        return promocionInterface.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a promotion by its unique identifier.
     *
     * @param id the unique identifier of the promotion
     * @return the promotion DTO if found, otherwise null
     */
    public promocionDto getPromocion(UUID id) {
        Optional<promocion> promocion = promocionInterface.findById(id);
        if (promocion.isPresent()) {
            return convertToDTO(promocion.get());
        }
        return null;
    }

    /**
     * Saves a given promocionDto by converting it to an entity, saving it using the
     * promocionInterface,
     * and then converting the saved entity back to a DTO.
     *
     * @param promocionDto the DTO object to be saved
     * @return the saved promocionDto after conversion from the entity
     */
    public promocionDto save(promocionDto promocionDto) {
        promocion promocion = convertToEntity(promocionDto);
        promocion = promocionInterface.save(promocion);
        return convertToDTO(promocion);
    }

    /**
     * Updates an existing promotion.
     *
     * @param promocionDto the promotion data transfer object containing the updated
     *                     details
     * @return the updated promotion data transfer object
     */
    public promocionDto update(promocionDto promocionDto) {
        promocion promocion = convertToEntity(promocionDto);
        promocion = promocionInterface.save(promocion);
        return convertToDTO(promocion);
    }

    /**
     * Deletes a promotion by its unique identifier.
     *
     * @param id the unique identifier of the promotion to be deleted
     * @return true if the promotion was successfully deleted
     */
    public boolean deletePromocion(UUID id) {
        promocionInterface.deleteById(id);
        return true;
    }

}
