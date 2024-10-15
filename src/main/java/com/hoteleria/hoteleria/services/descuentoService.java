package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.descuentoDto;
import com.hoteleria.hoteleria.dtos.descuentoDto.promocionDTO;
import com.hoteleria.hoteleria.interfaces.descuentoInterface;
import com.hoteleria.hoteleria.interfaces.promocionInterface;
import com.hoteleria.hoteleria.models.*;

@Service
public class descuentoService {

    @Autowired
    private descuentoInterface descuentoInterface;

    @Autowired
    private promocionInterface promocionInterface;

    /**
     * Converts a {@link descuentos} entity to a {@link descuentoDto}.
     *
     * @param descuento the {@link descuentos} entity to be converted
     * @return the converted {@link descuentoDto}
     */
    private descuentoDto convertToDTO(descuentos descuento) {
        descuentoDto descuentoDto = new descuentoDto();
        descuentoDto.setId(descuento.getId());

        promocionDTO promocionDto = new promocionDTO();
        promocionDto.setId(descuento.getPromocion().getId());
        promocionDto.setDescripcion(descuento.getPromocion().getDescripcion());
        descuentoDto.setPromocion(promocionDto);

        descuentoDto.setCodigo(descuento.getCodigo());
        descuentoDto.setTipo_de_descuento(descuento.getTipo_de_descuento());
        descuentoDto.setValor_descuento(descuento.getValor_descuento());
        descuentoDto.setFecha_inicio(descuento.getFecha_inicio());
        descuentoDto.setFecha_fin(descuento.getFecha_fin());
        descuentoDto.setEstado(descuento.getEstado());
        return descuentoDto;
    }

    /**
     * Converts a {@link descuentoDto} object to a {@link descuentos} entity.
     *
     * @param descuentoDto the DTO object containing the discount details to be
     *                     converted.
     * @return a {@link descuentos} entity built from the provided
     *         {@link descuentoDto}.
     * @throws IllegalArgumentException if the associated {@link promocion} is not
     *                                  found.
     */
    private descuentos convertToEntity(descuentoDto descuentoDto) {

        promocion promocion = promocionInterface.findById(descuentoDto.getPromocion().getId())
                .orElseThrow(() -> new IllegalArgumentException("Promocion not found"));

        descuentos.Builder descuento = new descuentos.Builder()
                .id(descuentoDto.getId())
                .codigo(descuentoDto.getCodigo())
                .promocion(promocion)
                .tipo_de_descuento(descuentoDto.getTipo_de_descuento())
                .valor_descuento(descuentoDto.getValor_descuento())
                .fecha_inicio(descuentoDto.getFecha_inicio())
                .fecha_fin(descuentoDto.getFecha_fin())
                .estado(descuentoDto.getEstado());
        return descuento.build();
    }

    /**
     * Retrieves a list of discount DTOs.
     *
     * This method fetches all discounts from the data source using the
     * descuentoInterface, converts each discount entity to a DTO, and
     * returns the list of DTOs.
     *
     * @return a list of {@link descuentoDto} objects representing the discounts.
     */
    public List<descuentoDto> getDescuentos() {
        List<descuentos> descuentos = descuentoInterface.findAll();
        return descuentos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a descuentoDto object by its UUID.
     *
     * @param id the UUID of the descuento to retrieve
     * @return the corresponding descuentoDto object, or null if not found
     */
    public descuentoDto getDescuento(UUID id) {
        descuentos descuento = descuentoInterface.findById(id).orElse(null);
        if (descuento == null) {
            return null;
        }
        return convertToDTO(descuento);
    }

    /**
     * Retrieves a descuentoDto object based on the provided discount code.
     *
     * @param codigo the discount code to search for
     * @return the descuentoDto object if found, otherwise null
     */
    public descuentoDto getDescuentoByCode(String codigo) {
        descuentos descuento = descuentoInterface.findByCodigo(codigo);
        if (descuento == null) {
            return null;
        }
        return convertToDTO(descuento);
    }

    /**
     * Saves a descuentoDto object by converting it to an entity,
     * saving it using the descuentoInterface, and then converting
     * the saved entity back to a DTO.
     *
     * @param descuentoDto the DTO object to be saved
     * @return the saved descuentoDto object
     */
    public descuentoDto save(descuentoDto descuentoDto) {
        descuentos descuento = convertToEntity(descuentoDto);
        descuento = descuentoInterface.save(descuento);
        return convertToDTO(descuento);
    }

    /**
     * Deletes a discount entry identified by the given UUID.
     *
     * @param id the UUID of the discount entry to be deleted
     * @return true if the discount entry was successfully deleted
     */
    public boolean deleteDescuento(UUID id) {
        descuentoInterface.deleteById(id);
        return true;
    }

    /**
     * Updates an existing discount record.
     *
     * @param descuentoDto the DTO object containing the updated discount
     *                     information.
     * @return the updated discount information as a DTO.
     */
    public descuentoDto update(descuentoDto descuentoDto) {
        descuentos descuento = convertToEntity(descuentoDto);
        descuento = descuentoInterface.save(descuento);
        return convertToDTO(descuento);
    }
}
