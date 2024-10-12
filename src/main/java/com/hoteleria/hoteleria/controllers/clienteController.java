package com.hoteleria.hoteleria.controllers;

import com.hoteleria.hoteleria.dtos.clienteDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.cliente;
import com.hoteleria.hoteleria.services.clienteService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class clienteController {

    @Autowired
    private clienteService clienteService;

    @GetMapping("/clientes") // get all clientes
    public ResponseEntity<responseHelper<List<clienteDto>>> getAllClientes() {
        return handleResponse(() -> clienteService.getAll());
    }

    @GetMapping("/clientes/nit") // get cliente by nit
    public ResponseEntity<responseHelper<clienteDto>> getClienteByNit(
            @RequestBody Map<String, String> requestBody) {
        String nit = requestBody.get("nit");

        clienteDto cliente = clienteService.getByNit(nit);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Cliente does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, cliente, null));
    }

    @GetMapping("/clientes/uuid") // get cliente by id
    public ResponseEntity<responseHelper<clienteDto>> getClienteById(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        clienteDto cliente = clienteService.getById(id);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Cliente does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, cliente, null));

    }

    @PostMapping("/clientes") // create cliente
    public ResponseEntity<responseHelper<cliente>> createCliente(@Valid @RequestBody cliente cliente) {
        // clienteDto clientExist = clienteService.getByNit(cliente.getNit());
        try {
            // if (clientExist != null) {
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            // .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
            // "Cliente already exists"));
            // }
            clienteService.save(cliente);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, cliente, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }

    }

    @PutMapping("/clientes") // update cliente
    public ResponseEntity<responseHelper<clienteDto>> updateCliente(@RequestBody clienteDto cliente) {
        clienteDto clientExist = clienteService.getById(cliente.getId());
        try {
            if (clientExist == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                                "Cliente does not exist"));
            }
            clienteService.update(cliente);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, cliente, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e));
        }
    }

    @DeleteMapping("/clientes") // delete cliente
    public ResponseEntity<responseHelper<Boolean>> deleteCliente(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        boolean deleted = clienteService.delete(id);
        try {
            if (!deleted) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, false,
                                "Cliente does not exist"));
            }
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, deleted, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, false, e));
        }
    }

    private <T> ResponseEntity<responseHelper<T>> handleResponse(Supplier<T> action) {
        try {
            T result = action.get();
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, result, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e));
        }
    }
}
