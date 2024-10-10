package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.clienteDto;
import com.hoteleria.hoteleria.interfaces.clienteInterface;
import com.hoteleria.hoteleria.models.cliente;

@Service
public class clienteService {

    @Autowired
    private clienteInterface clienteInterface;

    private clienteDto convertToDto(cliente cliente) {
        clienteDto dto = new clienteDto();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setNit(cliente.getNit());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        dto.setRole(cliente.getRole());
        dto.setReservaciones(cliente.getReservaciones());
        return dto;
    }

    private cliente convertToEntity(clienteDto dto) {
        cliente cliente = new cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setNit(dto.getNit());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());
        cliente.setRole(dto.getRole());
        cliente.setReservaciones(dto.getReservaciones());
        return cliente;
    }

    public List<clienteDto> getAll() {
        return clienteInterface.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public clienteDto getById(UUID id) {
        return convertToDto(clienteInterface.findById(id).get());
    }

    public clienteDto getByNit(String nit) {
        return convertToDto(clienteInterface.findByNit(nit));
    }

    public void save(cliente clienteDto) {
        clienteInterface.save(clienteDto);
    }

    public boolean delete(UUID id) {
        if (clienteInterface.existsById(id)) {
            clienteInterface.deleteById(id);
            return true;
        }
        return false;
    }

    public void update(clienteDto clienteDto) {
        cliente cliente = convertToEntity(clienteDto);
        clienteInterface.save(cliente);
    }
}
