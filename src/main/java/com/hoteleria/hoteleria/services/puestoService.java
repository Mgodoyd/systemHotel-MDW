package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.puestoDto;
import com.hoteleria.hoteleria.interfaces.*;
import com.hoteleria.hoteleria.models.puesto;

/**
 * Service class for managing Puesto entities.
 * This class provides methods to perform CRUD operations and conversions
 * between Puesto entities and Puesto DTOs.
 */
@Service
public class puestoService {

    @Autowired
    private puestoInterface puestoInterface;

    /**
     * Retrieves a list of all Puesto entities and converts them to DTOs.
     *
     * @return a list of Puesto DTOs.
     */
    public List<puestoDto> findAll() {
        return puestoInterface.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds a puesto by its unique identifier.
     *
     * @param id the unique identifier of the puesto
     * @return the puestoDto corresponding to the found puesto, or null if no puesto
     *         is found
     */
    public puestoDto findById(UUID id) {
        Optional<puesto> puesto = puestoInterface.findById(id);
        if (puesto.isPresent()) {
            return convertToDto(puesto.get());
        }
        return null;
    }

    /**
     * Finds a puesto by its name.
     *
     * @param name the name of the puesto to find
     * @return the puestoDto corresponding to the found puesto, or null if no puesto
     *         is found
     */
    public puestoDto findByName(String name) {
        puesto puesto = puestoInterface.findByName(name);
        if (puesto == null) {
            return null;
        }
        return convertToDto(puesto);
    }

    /**
     * Saves the given puesto entity.
     *
     * @param puesto the puesto entity to be saved
     * @return the saved puesto entity
     */
    public puesto save(puesto puesto) {
        return puestoInterface.save(puesto);
    }

    /**
     * Deletes a record with the specified UUID.
     *
     * @param id the UUID of the record to be deleted
     * @return true if the deletion was successful
     */
    public boolean delete(UUID id) {
        puestoInterface.deleteById(id);
        return true;
    }

    /**
     * Updates an existing puesto entity with the provided puestoDto data.
     *
     * @param puestoDto the data transfer object containing the updated information
     *                  for the puesto entity
     * @return the updated puestoDto after the entity has been saved
     */
    public puestoDto update(puestoDto puestoDto) {
        puesto puesto = convertToEntity(puestoDto);
        puesto = puestoInterface.save(puesto);
        return convertToDto(puesto);

    }

    /**
     * Converts a puestoDto object to a puesto entity.
     *
     * @param puestoDto the DTO object containing the data to be converted
     * @return a new puesto entity populated with data from the provided puestoDto
     */
    private puesto convertToEntity(puestoDto puestoDto) {
        puesto puesto = new puesto();
        puesto.setId(puestoDto.getId());
        puesto.setName(puestoDto.getName());
        return puesto;
    }

    /**
     * Converts a puesto entity to a puestoDto.
     *
     * @param puesto the puesto entity to be converted
     * @return the converted puestoDto
     */
    private puestoDto convertToDto(puesto puesto) {
        puestoDto puestoDto = new puestoDto();
        puestoDto.setId(puesto.getId());
        puestoDto.setName(puesto.getName());
        return puestoDto;
    }
}