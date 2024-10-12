package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.servicioHabitacionDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.servicioHabitacionService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
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

        servicioHabitacionDto servicioHabitacion = servicioHabitacionService.getServicioHabitacion(id);
        if (servicioHabitacion == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "ServicioHabitacion does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, servicioHabitacion, null));
    }

    @PostMapping("/servicioHabitaciones") // create servicio
    public ResponseEntity<responseHelper<servicioHabitacionDto>> createServicioHabitacion(
            @RequestBody servicioHabitacionDto servicioHabitacionDto) {
        try {
            servicioHabitacionDto servicioHabitacion = servicioHabitacionService.save(servicioHabitacionDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, servicioHabitacion, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping("/servicioHabitaciones") // update servicio
    public ResponseEntity<responseHelper<servicioHabitacionDto>> updateServicioHabitacion(
            @RequestBody servicioHabitacionDto servicioHabitacionDto) {
        try {
            servicioHabitacionDto servicioHabitacion = servicioHabitacionService.update(servicioHabitacionDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, servicioHabitacion, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, e.getMessage()));
        }
    }

    @DeleteMapping("/servicioHabitaciones") // delete servicio
    public ResponseEntity<responseHelper<Boolean>> deleteServicioHabitacion(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        servicioHabitacionDto servicioHabitacion = servicioHabitacionService.getServicioHabitacion(id);

        try {
            if (servicioHabitacion == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, false,
                                "ServicioHabitacion does not exist"));
            }
            servicioHabitacionService.delete(id);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, true, null));
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
