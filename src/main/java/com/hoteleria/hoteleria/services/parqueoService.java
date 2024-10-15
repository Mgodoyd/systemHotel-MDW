package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.parqueoDto;
import com.hoteleria.hoteleria.dtos.parqueoDto.servicioDTO;
import com.hoteleria.hoteleria.interfaces.parqueoInterface;
import com.hoteleria.hoteleria.interfaces.serviciosInterface;
import com.hoteleria.hoteleria.models.parqueo;
import com.hoteleria.hoteleria.models.servicio;

@Service
public class parqueoService {
    @Autowired
    private parqueoInterface parqueoInterface;

    @Autowired
    private serviciosInterface serviciosInterface;

    /**
     * Converts a {@link parqueo} entity to a {@link parqueoDto}.
     *
     * @param parqueo the {@link parqueo} entity to be converted
     * @return the converted {@link parqueoDto}
     */
    private parqueoDto convertToDTO(parqueo parqueo) {
        parqueoDto parqueoDto = new parqueoDto();
        parqueoDto.setId(parqueo.getId());

        servicioDTO servicioDto = new servicioDTO();
        servicioDto.setId(parqueo.getServicio().getId());
        servicioDto.setNombre(parqueo.getServicio().getNombre());
        parqueoDto.setServicio(servicioDto);

        parqueoDto.setFecha_inicio(parqueo.getFecha_inicio());
        parqueoDto.setFecha_fin(parqueo.getFecha_fin());
        parqueoDto.setMonto(parqueo.getMonto());
        return parqueoDto;
    }

    /**
     * Converts a parqueoDto object to a parqueo entity.
     *
     * @param parqueoDto the DTO object containing the data to be converted
     * @return a parqueo entity built from the provided DTO
     * @throws IllegalArgumentException if the servicio associated with the DTO is
     *                                  not found
     */
    private parqueo convertToEntity(parqueoDto parqueoDto) {
        servicio servicio = serviciosInterface.findById(parqueoDto.getServicio().getId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio not found"));

        parqueo.Builder parqueoBuilder = new parqueo.Builder()
                .id(parqueoDto.getId())
                .servicio(servicio)
                .fecha_inicio(parqueoDto.getFecha_inicio())
                .fecha_fin(parqueoDto.getFecha_fin())
                .monto(parqueoDto.getMonto());
        return parqueoBuilder.build();

    }

    /**
     * Retrieves a list of all parqueos and converts them to DTOs.
     *
     * @return a list of parqueoDto objects representing the parqueos.
     */
    public List<parqueoDto> getParqueos() {
        List<parqueo> parqueos = parqueoInterface.findAll();
        return parqueos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a Parqueo (parking) entity by its unique identifier.
     *
     * @param id the unique identifier of the Parqueo entity to be retrieved.
     * @return a ParqueoDto object representing the Parqueo entity if found,
     *         otherwise null.
     */
    public parqueoDto getParqueo(UUID id) {
        Optional<parqueo> parqueo = parqueoInterface.findById(id);
        if (parqueo.isPresent()) {
            return convertToDTO(parqueo.get());
        }
        return null;
    }

    /**
     * Saves a Parqueo entity by converting the provided ParqueoDto to an entity,
     * saving it using the parqueoInterface, and then converting the saved entity
     * back to a ParqueoDto.
     *
     * @param parqueoDto the ParqueoDto object to be saved
     * @return the saved ParqueoDto object
     */
    public parqueoDto save(parqueoDto parqueoDto) {
        parqueo parqueo = convertToEntity(parqueoDto);
        parqueo = parqueoInterface.save(parqueo);
        return convertToDTO(parqueo);
    }

    /**
     * Updates an existing parqueo record.
     *
     * @param parqueoDto the DTO object containing the updated details of the
     *                   parqueo.
     * @return the updated parqueo DTO.
     * @throws IllegalArgumentException if the parqueo record to update does not
     *                                  exist.
     */
    public parqueoDto update(parqueoDto parqueoDto) {
        if (parqueoDto.getId() == null || !parqueoInterface.existsById(parqueoDto.getId())) {
            throw new IllegalArgumentException("El registro a actualizar no existe");
        }

        parqueo parqueo = convertToEntity(parqueoDto);
        parqueo = parqueoInterface.save(parqueo);
        return convertToDTO(parqueo);
    }

    /**
     * Deletes a record with the specified UUID from the database.
     *
     * @param id the UUID of the record to be deleted
     * @return true if the record was found and deleted, false otherwise
     */
    public boolean delete(UUID id) {
        if (parqueoInterface.existsById(id)) {
            parqueoInterface.deleteById(id);
            return true;
        }
        return false;
    }
}
