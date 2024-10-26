package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.clienteDto;
import com.hoteleria.hoteleria.dtos.reservacionDto;
import com.hoteleria.hoteleria.dtos.staffDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173",
        "https://mango-smoke-084edd70f.5.azurestaticapps.net" }, methods = { RequestMethod.POST,
                RequestMethod.GET,
                RequestMethod.DELETE,
                RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class reservacionController {
    @Autowired
    private reservacionService reservacionService;

    @Autowired
    private personalService personalService;

    @Autowired
    private clienteService clienteService;

    @GetMapping("/reservaciones") // get all reservaciones
    public ResponseEntity<responseHelper<List<reservacionDto>>> getAllReservaciones() {
        return handleResponse(() -> reservacionService.getAll());
    }

    @GetMapping("/reservaciones/id") // Get reservacion by id
    public ResponseEntity<responseHelper<reservacionDto>> getReservacionById(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        return handleResponse(() -> {
            reservacionDto reservacion = reservacionService.getById(id);
            if (reservacion == null) {
                throw new ResourceNotFoundException("Reservacion does not exist");
            }
            return reservacion;
        });
    }

    @PostMapping("/reservaciones") // Save reservacion
    public ResponseEntity<responseHelper<reservacionDto>> saveReservacion(
            @Valid @RequestBody reservacionDto reservacion) {
        return handleResponse(() -> {
            staffDto existing = personalService.findById(reservacion.getCliente().getId()).orElse(null);
            clienteDto existingNit = clienteService.getById(reservacion.getCliente().getId());

            if (existing == null && existingNit == null) {
                throw new ResourceNotFoundException("Cliente does not exist");
            }

            reservacionService.save(reservacion);

            return reservacion;
        });
    }

    @PatchMapping("/reservaciones") // Update reservacion
    public ResponseEntity<responseHelper<reservacionDto>> updateReservacion(
            @Valid @RequestBody reservacionDto reservacion) {
        return handleResponse(() -> {
            reservacionDto existingReservacion = reservacionService.getById(reservacion.getId());
            if (existingReservacion == null) {
                throw new ResourceNotFoundException("Reservacion does not exist");
            }
            reservacionService.update(reservacion);
            return reservacion;
        });
    }

    @DeleteMapping("/reservaciones") // Delete reservacion
    public ResponseEntity<responseHelper<Boolean>> deleteReservacion(@RequestBody Map<String, String> requestBody) {
        return handleResponse(() -> {
            UUID id = UUID.fromString(requestBody.get("id"));
            boolean result = reservacionService.delete(id);
            if (!result) {
                throw new ResourceNotFoundException("Reservacion does not exist");
            }
            return result;
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }
}
