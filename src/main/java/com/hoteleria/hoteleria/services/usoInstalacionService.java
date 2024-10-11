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
import com.hoteleria.hoteleria.models.servicio;
import com.hoteleria.hoteleria.models.usoInstalacion;

@Service
public class usoInstalacionService {
    @Autowired
    private usoInstalacionInterface usoInstalacionInterface;

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

    private usoInstalacion convertToEntity(usoInstalacionDto usoInstalacionDto) {
        usoInstalacion usoInstalacion = new usoInstalacion();
        usoInstalacion.setId(usoInstalacionDto.getId());

        servicio servicio = new servicio();
        servicio.setId(usoInstalacionDto.getServicio().getId());
        servicio.setNombre(usoInstalacionDto.getServicio().getNombre());
        usoInstalacion.setServicio(servicio);

        usoInstalacion.setDescripcion(usoInstalacionDto.getDescripcion());
        usoInstalacion.setMonto(usoInstalacionDto.getMonto());
        return usoInstalacion;
    }

    public List<usoInstalacionDto> getUsoInstalaciones() {
        List<usoInstalacion> usoInstalaciones = usoInstalacionInterface.findAll();
        return usoInstalaciones.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public usoInstalacionDto getUsoInstalacion(UUID id) {
        Optional<usoInstalacion> usoInstalacion = usoInstalacionInterface.findById(id);
        if (usoInstalacion.isPresent()) {
            return convertToDTO(usoInstalacion.get());
        }
        return null;
    }

    public boolean delete(UUID id) {
        usoInstalacionInterface.deleteById(id);
        return true;
    }

    public usoInstalacionDto save(usoInstalacionDto usoInstalacionDto) {
        usoInstalacion usoInstalacion = convertToEntity(usoInstalacionDto);
        usoInstalacion = usoInstalacionInterface.save(usoInstalacion);
        return convertToDTO(usoInstalacion);
    }

    public usoInstalacionDto update(usoInstalacionDto usoInstalacionDto) {
        usoInstalacion usoInstalacion = convertToEntity(usoInstalacionDto);
        usoInstalacion = usoInstalacionInterface.save(usoInstalacion);
        return convertToDTO(usoInstalacion);
    }

}
