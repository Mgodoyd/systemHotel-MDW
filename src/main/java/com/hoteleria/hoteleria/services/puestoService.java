package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.puestoDto;
import com.hoteleria.hoteleria.interfaces.*;
import com.hoteleria.hoteleria.models.puesto;

@Service
public class puestoService {

    @Autowired
    private puestoInterface puestoInterface;

    public List<puestoDto> findAll() {
        return puestoInterface.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public puestoDto findById(UUID id) {
        puesto puesto = puestoInterface.findById(id).orElse(null);
        return convertToDto(puesto);
    }

    public puestoDto findByName(String name) {
        puesto puesto = puestoInterface.findByName(name);
        if (puesto == null) {
            return null;
        }
        return convertToDto(puesto);
    }

    public puesto save(puesto puesto) {
        return puestoInterface.save(puesto);
    }

    public void deleteById(UUID id) {
        puestoInterface.deleteById(id);
    }

    public puestoDto update(puestoDto puestoDto) {
        puesto puesto = convertToEntity(puestoDto);
        return convertToDto(puestoInterface.save(puesto));
    }

    private puesto convertToEntity(puestoDto puestoDto) {
        puesto puesto = new puesto();
        puesto.setId(puestoDto.getId());
        puesto.setName(puestoDto.getName());
        return puesto;
    }

    private puestoDto convertToDto(puesto puesto) {
        puestoDto puestoDto = new puestoDto();
        puestoDto.setId(puesto.getId());
        puestoDto.setName(puesto.getName());
        return puestoDto;
    }
}