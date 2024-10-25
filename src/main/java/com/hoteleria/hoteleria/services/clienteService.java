package com.hoteleria.hoteleria.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.dtos.clienteDto;
import com.hoteleria.hoteleria.interfaces.clienteInterface;
import com.hoteleria.hoteleria.models.cliente;

@Service
public class clienteService {

    @Autowired
    private clienteInterface clienteInterface;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Converts a {@link cliente} entity to a {@link clienteDto}.
     *
     * @param cliente the {@link cliente} entity to convert
     * @return the converted {@link clienteDto}
     */
    private clienteDto convertToDto(cliente cliente) {
        clienteDto dto = new clienteDto();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setNit(cliente.getNit());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        dto.setPassword(cliente.getPassword());
        dto.setRole(cliente.getRole());
        dto.setReservaciones(cliente.getReservaciones());
        return dto;
    }

    /**
     * Converts a clienteDto object to a cliente entity.
     *
     * @param dto the clienteDto object to be converted
     * @return a cliente entity built from the provided dto
     */
    private cliente convertToEntity(clienteDto dto) {
        return new cliente.Builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .nit(dto.getNit())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .direccion(dto.getDireccion())
                .role(dto.getRole())
                .reservaciones(dto.getReservaciones())
                .build();

    }

    /**
     * Retrieves all clients and converts them to DTOs.
     *
     * @return a list of client DTOs.
     */
    public List<clienteDto> getAll() {
        return clienteInterface.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Retrieves a clienteDto object by its unique identifier.
     *
     * @param id the unique identifier of the cliente to retrieve
     * @return the clienteDto object if found, otherwise null
     */
    public clienteDto getById(UUID id) {
        Optional<cliente> cliente = clienteInterface.findById(id);
        if (cliente.isPresent()) {
            return convertToDto(cliente.get());
        }
        return null;
    }

    /**
     * Retrieves a clienteDto object by its unique nit.
     *
     * @param nit the unique nit of the cliente to retrieve
     * @return the clienteDto object if found, otherwise null
     */

    public clienteDto getByNit(String nit) {
        Optional<cliente> cliente = clienteInterface.findByNit(nit);
        if (cliente.isPresent()) {
            return convertToDto(cliente.get());
        }

        return null;
    }

    public clienteDto getByEmail(String email) {
        Optional<cliente> cliente = clienteInterface.findByEmail(email);
        if (cliente.isPresent()) {
            return convertToDto(cliente.get());
        }

        return null;
    }

    /**
     * Saves the given cliente object using the clienteInterface.
     *
     * @param clienteDto the cliente object to be saved
     */
    public clienteDto save(clienteDto clienteDto) {
        cliente cliente = convertToEntity(clienteDto);
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        clienteInterface.save(cliente);
        return clienteDto;
    }

    /**
     * Deletes a client by their unique identifier.
     *
     * @param id the unique identifier of the client to be deleted
     * @return true if the client was successfully deleted, false if the client does
     *         not exist
     */
    public boolean delete(UUID id) {
        if (clienteInterface.existsById(id)) {
            clienteInterface.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Updates an existing cliente entity with the provided clienteDto data.
     *
     * @param clienteDto the data transfer object containing updated cliente
     *                   information
     */
    public clienteDto update(clienteDto clienteDto) {
        cliente cliente = convertToEntity(clienteDto);
        clienteInterface.save(cliente);
        return clienteDto;
    }
}
