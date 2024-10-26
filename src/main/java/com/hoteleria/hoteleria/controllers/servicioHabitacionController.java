package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.servicioHabitacionDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.servicioHabitacionService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173",
        "https://mango-smoke-084edd70f.5.azurestaticapps.net" }, methods = { RequestMethod.POST,
                RequestMethod.GET,
                RequestMethod.DELETE,
                RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class servicioHabitacionController {

    @Autowired
    private servicioHabitacionService servicioHabitacionService;

    @GetMapping("/servicioHabitaciones") // get all servicio
    public ResponseEntity<responseHelper<List<servicioHabitacionDto>>> getAllServicioHabitaciones() {
        return handleResponse(() -> servicioHabitacionService.getServicioHabitaciones());
    }

    @GetMapping("/servicioHabitaciones/id") // get servicio by id
    public ResponseEntity<responseHelper<servicioHabitacionDto>> getServicioHabitacionById(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        return handleResponse(() -> {
            servicioHabitacionDto servicioHabitacion = servicioHabitacionService.getServicioHabitacion(id);
            if (servicioHabitacion == null) {
                throw new IllegalArgumentException("ServicioHabitacion does not exist");
            }
            return servicioHabitacion;
        });
    }

    @PostMapping("/servicioHabitaciones") // create servicio
    public ResponseEntity<responseHelper<servicioHabitacionDto>> createServicioHabitacion(
            @RequestBody servicioHabitacionDto servicioHabitacionDto) {
        return handleResponse(() -> servicioHabitacionService.save(servicioHabitacionDto));
    }

    @PatchMapping("/servicioHabitaciones") // update servicio
    public ResponseEntity<responseHelper<servicioHabitacionDto>> updateServicioHabitacion(
            @RequestBody servicioHabitacionDto servicioHabitacionDto) {
        servicioHabitacionDto servicioHabiExist = servicioHabitacionService
                .getServicioHabitacion(servicioHabitacionDto.getId());
        return handleResponse(() -> {
            if (servicioHabiExist == null) {
                throw new IllegalArgumentException("ServicioHabitacion does not exist");
            }
            return servicioHabitacionService.save(servicioHabitacionDto);
        });
    }

    @DeleteMapping("/servicioHabitaciones") // delete servicio
    public ResponseEntity<responseHelper<Boolean>> deleteServicioHabitacion(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        return handleResponse(() -> {
            servicioHabitacionDto servicioHabitacion = servicioHabitacionService.getServicioHabitacion(id);
            if (servicioHabitacion == null) {
                throw new IllegalArgumentException("ServicioHabitacion does not exist");
            }
            return servicioHabitacionService.delete(id);
        });
    }

    private <T> ResponseEntity<responseHelper<T>> handleResponse(Supplier<T> action) {
        try {
            T result = action.get();
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, result, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }
}
