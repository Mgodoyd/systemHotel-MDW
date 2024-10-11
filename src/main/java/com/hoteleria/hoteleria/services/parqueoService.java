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
import com.hoteleria.hoteleria.models.parqueo;
import com.hoteleria.hoteleria.models.servicio;

@Service
public class parqueoService {
    @Autowired
    private parqueoInterface parqueoInterface;

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

    private parqueo convertToEntity(parqueoDto parqueoDto) {
        parqueo parqueo = new parqueo();
        parqueo.setId(parqueoDto.getId());

        servicio servicio = new servicio();
        servicio.setId(parqueoDto.getServicio().getId());
        servicio.setNombre(parqueoDto.getServicio().getNombre());
        parqueo.setServicio(servicio);

        parqueo.setFecha_inicio(parqueoDto.getFecha_inicio());
        parqueo.setFecha_fin(parqueoDto.getFecha_fin());
        parqueo.setMonto(parqueoDto.getMonto());
        return parqueo;
    }

    public List<parqueoDto> getParqueos() {
        List<parqueo> parqueos = parqueoInterface.findAll();
        return parqueos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public parqueoDto getParqueo(UUID id) {
        Optional<parqueo> parqueo = parqueoInterface.findById(id);
        if (parqueo.isPresent()) {
            return convertToDTO(parqueo.get());
        }
        return null;
    }

    public parqueoDto save(parqueoDto parqueoDto) {
        parqueo parqueo = convertToEntity(parqueoDto);
        parqueo = parqueoInterface.save(parqueo);
        return convertToDTO(parqueo);
    }

    public parqueoDto update(parqueoDto parqueoDto) {
        if (parqueoDto.getId() == null || !parqueoInterface.existsById(parqueoDto.getId())) {
            throw new IllegalArgumentException("El registro a actualizar no existe");
        }

        parqueo parqueo = convertToEntity(parqueoDto);
        parqueo = parqueoInterface.save(parqueo);
        return convertToDTO(parqueo);
    }

    public boolean delete(UUID id) {
        if (parqueoInterface.existsById(id)) {
            parqueoInterface.deleteById(id);
            return true;
        }
        return false;
    }
}
