package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.descuentoDto;
import com.hoteleria.hoteleria.dtos.promocionDto;
import com.hoteleria.hoteleria.dtos.descuentoDto.promocionDTO;
import com.hoteleria.hoteleria.interfaces.descuentoInterface;
import com.hoteleria.hoteleria.models.descuentos;
import com.hoteleria.hoteleria.models.promocion;

@Service
public class descuentoService {

    @Autowired
    private descuentoInterface descuentoInterface;

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

    private descuentos convertToEntity(descuentoDto descuentoDto) {
        descuentos descuento = new descuentos();
        descuento.setId(descuentoDto.getId());

        promocion promocion = new promocion();
        promocion.setId(descuentoDto.getPromocion().getId());
        promocion.setDescripcion(descuentoDto.getPromocion().getDescripcion());
        descuento.setPromocion(promocion);

        descuento.setPromocion(promocion);
        descuento.setCodigo(descuentoDto.getCodigo());
        descuento.setTipo_de_descuento(descuentoDto.getTipo_de_descuento());
        descuento.setValor_descuento(descuentoDto.getValor_descuento());
        descuento.setFecha_inicio(descuentoDto.getFecha_inicio());
        descuento.setFecha_fin(descuentoDto.getFecha_fin());
        descuento.setEstado(descuentoDto.getEstado());
        return descuento;
    }

    public List<descuentoDto> getDescuentos() {
        List<descuentos> descuentos = descuentoInterface.findAll();
        return descuentos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public descuentoDto getDescuento(UUID id) {
        descuentos descuento = descuentoInterface.findById(id).orElse(null);
        if (descuento == null) {
            return null;
        }
        return convertToDTO(descuento);
    }

    public descuentoDto getDescuentoByCode(String codigo) {
        descuentos descuento = descuentoInterface.findByCodigo(codigo);
        if (descuento == null) {
            return null;
        }
        return convertToDTO(descuento);
    }

    public descuentoDto save(descuentoDto descuentoDto) {
        descuentos descuento = convertToEntity(descuentoDto);
        descuento = descuentoInterface.save(descuento);
        return convertToDTO(descuento);
    }

    public boolean deleteDescuento(UUID id) {
        descuentoInterface.deleteById(id);
        return true;
    }

    public descuentoDto update(descuentoDto descuentoDto) {
        descuentos descuento = convertToEntity(descuentoDto);
        descuento = descuentoInterface.save(descuento);
        return convertToDTO(descuento);
    }
}
