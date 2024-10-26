package com.hoteleria.hoteleria.controllers;

import com.hoteleria.hoteleria.dtos.clienteDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.clienteService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" ,"https://mango-smoke-084edd70f.5.azurestaticapps.net"}, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class clienteController {

    @Autowired
    private clienteService clienteService;

    @GetMapping("/clientes") // get all clientes
    public ResponseEntity<responseHelper<List<clienteDto>>> getAllClientes() {
        return handleResponse(() -> clienteService.getAll());
    }

    @GetMapping("/clientes/nit") // get cliente by nit
    public ResponseEntity<responseHelper<clienteDto>> getClienteByNit(@RequestBody Map<String, String> requestBody) {
        String nit = requestBody.get("nit");
        return handleResponse(() -> {
            clienteDto cliente = clienteService.getByNit(nit);
            if (cliente == null) {
                throw new ResourceNotFoundException("Cliente does not exist");
            }
            return cliente;
        });
    }

    @GetMapping("/clientes/uuid") // get cliente by id
    public ResponseEntity<responseHelper<clienteDto>> getClienteById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        return handleResponse(() -> {
            clienteDto cliente = clienteService.getById(id);
            if (cliente == null) {
                throw new ResourceNotFoundException("Cliente does not exist");
            }
            return cliente;
        });
    }

    @PostMapping("/clientes") // create cliente
    public ResponseEntity<responseHelper<clienteDto>> createCliente(@Valid @RequestBody clienteDto clienteDto) {
        return handleResponse(() -> {
            clienteDto existingCliente = clienteService.getByNit(clienteDto.getNit());
            if (existingCliente != null) {
                throw new IllegalArgumentException("Cliente already exists");
            }
            return clienteService.save(clienteDto);
        });
    }

    @PatchMapping("/clientes") // update cliente
    public ResponseEntity<responseHelper<clienteDto>> updateCliente(@RequestBody clienteDto clienteDto) {
        return handleResponse(() -> {
            clienteDto existingCliente = clienteService.getById(clienteDto.getId());
            if (existingCliente == null) {
                throw new ResourceNotFoundException("Cliente does not exist");
            }
            return clienteService.update(clienteDto);
        });
    }

    @DeleteMapping("/clientes") // delete cliente
    public ResponseEntity<responseHelper<Boolean>> deleteCliente(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        return handleResponse(() -> {
            boolean deleted = clienteService.delete(id);
            if (!deleted) {
                throw new ResourceNotFoundException("Cliente does not exist");
            }
            return deleted;
        });
    }

    // Helper method to handle responses
    private <T> ResponseEntity<responseHelper<T>> handleResponse(Supplier<T> action) {
        try {
            T result = action.get();
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, result, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new responseHelper<>("Error", HttpStatus.NOT_FOUND, null, e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }
}
