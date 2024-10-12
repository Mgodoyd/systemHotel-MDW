package com.hoteleria.hoteleria.controllers;

import com.hoteleria.hoteleria.dtos.habitacionDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.habitación;
import com.hoteleria.hoteleria.services.habitacionService;

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
public class habitaciónController {

    @Autowired
    private habitacionService habitacionService;

    @GetMapping("/habitaciones") // get all habitaciones
    public ResponseEntity<responseHelper<List<habitacionDto>>> getAllHabitaciones() {
        return handleResponse(() -> habitacionService.findAll());
    }

    @GetMapping("/habitaciones/number") // get habitacion by numero
    public ResponseEntity<responseHelper<habitacionDto>> getHabitacionbyNumero(
            @RequestBody Map<String, String> requestBody) {
        String numero = requestBody.get("number");

        habitacionDto habitacion = habitacionService.findByNumero(numero);
        if (habitacion == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Habitacion does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, habitacion, null));
    }

    @GetMapping("/habitaciones/uuid") // get habitacion by id
    public ResponseEntity<responseHelper<habitacionDto>> getHabitacionbyId(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        habitacionDto habitacion = habitacionService.findById(id);
        if (habitacion == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Habitacion does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, habitacion, null));
    }

    @PostMapping("/habitaciones")
    public ResponseEntity<responseHelper<habitación>> createHabitacion(
            @Valid @RequestBody habitación habitacion) {
        try {
            // Verificar si la habitación ya existe
            // habitacionDto habitacionExist =
            // habitacionService.findByNumero(habitacion.getNumero());
            // if (habitacionExist != null) {
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            // .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
            // "Habitacion already exists"));
            // }

            // Guardar la habitación utilizando el DTO
            habitacionService.save(habitacion);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, habitacion, null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PutMapping("/habitaciones") // update habitacion
    public ResponseEntity<responseHelper<habitacionDto>> updateHabitacion(@RequestBody habitacionDto habitacionDto) {
        habitacionDto habitacionExist = habitacionService.findById(habitacionDto.getId());
        try {
            if (habitacionExist == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                                "Habitacion does not exist"));
            }
            // Actualizar la habitación utilizando el DTO
            habitacionService.update(habitacionDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, habitacionDto, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @DeleteMapping("/habitaciones") // delete habitacion
    public ResponseEntity<responseHelper<Object>> deleteHabitacion(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        habitacionDto habitacion = habitacionService.findById(id);
        try {
            if (habitacion == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                                "Habitacion does not exist"));
            }
            // Eliminar la habitación utilizando el ID
            return ResponseEntity
                    .ok(new responseHelper<>("Success", HttpStatus.OK, habitacionService.deleteById(id), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, false, e.getMessage()));
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
