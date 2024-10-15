package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.usoInstalacionDto;
import com.hoteleria.hoteleria.dtos.usoInstalacionDto.servicioDTO;
import com.hoteleria.hoteleria.interfaces.usoInstalacionInterface;
import com.hoteleria.hoteleria.models.*;

@Service
public class usoInstalacionService {
    @Autowired
    private usoInstalacionInterface usoInstalacionInterface;

    /**
     * Converts a usoInstalacion entity to a usoInstalacionDto.
     *
     * @param usoInstalacion the usoInstalacion entity to be converted
     * @return the converted usoInstalacionDto
     */
    private usoInstalacionDto convertToDTO(usoInstalacion usoInstalacion) {
        usoInstalacionDto usoInstalacionDto = new usoInstalacionDto();
        usoInstalacionDto.setId(usoInstalacion.getId());

        servicioDTO servicioDto = new servicioDTO();
        servicioDto.setId(usoInstalacion.getServicio().getId());
        servicioDto.setNombre(usoInstalacion.getServicio().getNombre());
        usoInstalacionDto.setServicio(servicioDto);

        usoInstalacionDto.setDescripcion(usoInstalacion.getDescripcion());
        usoInstalacionDto.setMonto(usoInstalacion.getMonto());
        return usoInstalacionDto;
    }

    /**
     * Converts a usoInstalacionDto object to a usoInstalacion entity.
     *
     * @param usoInstalacionDto the DTO object to be converted
     * @return the converted usoInstalacion entity
     */
    private usoInstalacion convertToEntity(usoInstalacionDto usoInstalacionDto) {
        usoInstalacion.Builder builder = new usoInstalacion.Builder()
                .id(usoInstalacionDto.getId())
                .servicio(new servicio.Builder().id(usoInstalacionDto.getServicio().getId()).build())
                .descripcion(usoInstalacionDto.getDescripcion())
                .monto(usoInstalacionDto.getMonto());
        return builder.build();
    }

    /**
     * Retrieves a list of usoInstalacionDto objects.
     *
     * This method fetches all usoInstalacion entities from the database,
     * converts them to usoInstalacionDto objects, and returns them as a list.
     *
     * @return a list of usoInstalacionDto objects
     */
    public List<usoInstalacionDto> getUsoInstalaciones() {
        List<usoInstalacion> usoInstalaciones = usoInstalacionInterface.findAll();
        return usoInstalaciones.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a usoInstalacionDto object by its unique identifier.
     *
     * @param id the unique identifier of the usoInstalacion to retrieve
     * @return the corresponding usoInstalacionDto if found, otherwise null
     */
    public usoInstalacionDto getUsoInstalacion(UUID id) {
        Optional<usoInstalacion> usoInstalacion = usoInstalacionInterface.findById(id);
        if (usoInstalacion.isPresent()) {
            return convertToDTO(usoInstalacion.get());
        }
        return null;
    }

    /**
     * Deletes a record with the specified UUID.
     *
     * @param id the UUID of the record to be deleted
     * @return true if the deletion was successful
     */
    public boolean delete(UUID id) {
        usoInstalacionInterface.deleteById(id);
        return true;
    }

    /**
     * Saves the given usoInstalacionDto by converting it to an entity,
     * saving it using the usoInstalacionInterface, and then converting
     * the saved entity back to a DTO.
     *
     * @param usoInstalacionDto the DTO to be saved
     * @return the saved usoInstalacionDto
     */
    public usoInstalacionDto save(usoInstalacionDto usoInstalacionDto) {
        usoInstalacion usoInstalacion = convertToEntity(usoInstalacionDto);
        usoInstalacion = usoInstalacionInterface.save(usoInstalacion);
        return convertToDTO(usoInstalacion);
    }

    /**
     * Updates an existing usoInstalacion entity with the provided
     * usoInstalacionDto.
     * Converts the DTO to an entity, saves the updated entity, and then converts it
     * back to a DTO.
     *
     * @param usoInstalacionDto the DTO containing the updated information for the
     *                          usoInstalacion entity
     * @return the updated usoInstalacionDto after the entity has been saved
     */
    public usoInstalacionDto update(usoInstalacionDto usoInstalacionDto) {
        usoInstalacion usoInstalacion = convertToEntity(usoInstalacionDto);
        usoInstalacion = usoInstalacionInterface.save(usoInstalacion);
        return convertToDTO(usoInstalacion);
    }

}
