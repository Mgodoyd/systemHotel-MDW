package com.hoteleria.hoteleria.controllers;

import com.hoteleria.hoteleria.dtos.habitacionDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.habitacionService;

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
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" , "https://mango-smoke-084edd70f.5.azurestaticapps.net"}, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class habitaciónController {

    @Autowired
    private habitacionService habitacionService;

    @GetMapping("/habitaciones") // get all habitaciones
    public ResponseEntity<responseHelper<List<habitacionDto>>> getAllHabitaciones() {
        return handleResponse(() -> habitacionService.findAll());
    }

    @GetMapping("/habitaciones/number") // Get habitacion by numero
    public ResponseEntity<responseHelper<habitacionDto>> getHabitacionbyNumero(
            @RequestBody Map<String, String> requestBody) {
        String numero = requestBody.get("number");
        return handleResponse(() -> {
            habitacionDto habitacion = habitacionService.findByNumero(numero);
            if (habitacion == null) {
                throw new ResourceNotFoundException("Habitacion does not exist");
            }
            return habitacion;
        });
    }

    @GetMapping("/habitaciones/uuid") // Get habitacion by id
    public ResponseEntity<responseHelper<habitacionDto>> getHabitacionbyId(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        return handleResponse(() -> {
            habitacionDto habitacion = habitacionService.findById(id);
            if (habitacion == null) {
                throw new ResourceNotFoundException("Habitacion does not exist");
            }
            return habitacion;
        });
    }

    @PostMapping("/habitaciones") // Create habitacion
    public ResponseEntity<responseHelper<habitacionDto>> createHabitacion(
            @Valid @RequestBody habitacionDto habitacion) {
        return handleResponse(() -> {
            habitacionDto habitacionDto = habitacionService.findByNumero(habitacion.getNumero());
            if (habitacionDto != null) {
                throw new ResourceNotFoundException("Habitacion already exists");
            }
            return habitacionService.save(habitacion);
        });
    }

    @PatchMapping("/habitaciones") // Update habitacion
    public ResponseEntity<responseHelper<habitacionDto>> updateHabitacion(@RequestBody habitacionDto habitacionDto) {
        return handleResponse(() -> {
            habitacionDto habitacionExist = habitacionService.findById(habitacionDto.getId());
            if (habitacionExist == null) {
                throw new ResourceNotFoundException("Habitacion does not exist");
            }

            habitacionDto habitacionUpdate = habitacionService.update(habitacionDto);
            return habitacionUpdate;
        });
    }

    @DeleteMapping("/habitaciones") // Delete habitacion
    public ResponseEntity<responseHelper<Object>> deleteHabitacion(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        return handleResponse(() -> {
            habitacionDto habitacion = habitacionService.findById(id);
            if (habitacion == null) {
                throw new ResourceNotFoundException("Habitacion does not exist");
            }
            habitacionService.deleteById(id);
            return true;
        });
    }

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
