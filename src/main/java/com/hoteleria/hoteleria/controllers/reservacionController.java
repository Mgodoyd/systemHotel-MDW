package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.reservacionDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.reservacion;
import com.hoteleria.hoteleria.services.reservacionService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:8100" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class reservacionController {
    @Autowired
    private reservacionService reservacionService;

    @GetMapping("/reservaciones") // get all reservaciones
    public ResponseEntity<responseHelper<List<reservacionDto>>> getAllReservaciones() {
        return handleResponse(() -> reservacionService.getAll());
    }

    @GetMapping("/reservaciones/id") // get reservacion by id
    public ResponseEntity<responseHelper<reservacionDto>> getReservacionById(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        reservacionDto reservacion = reservacionService.getById(id);
        if (reservacion == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Reservacion does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, reservacion, null));
    }

    @PostMapping("/reservaciones") // save reservacion
    public ResponseEntity<responseHelper<reservacionDto>> saveReservacion(@Valid @RequestBody reservacion reservacion) {
        try {
            reservacionDto newreservacion = reservacionService.save(reservacion);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, newreservacion, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping("/reservaciones") // update reservacion
    public ResponseEntity<responseHelper<reservacionDto>> updateReservacion(
            @Valid @RequestBody reservacionDto reservacion) {
        try {
            reservacionService.update(reservacion);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, reservacion, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @DeleteMapping("/reservaciones") // delete reservacion
    public ResponseEntity<responseHelper<Boolean>> deleteReservacion(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        reservacionDto reservacion = reservacionService.getById(id);

        try {
            if (reservacion == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, false,
                                "Reservacion does not exist"));
            }

            reservacionService.delete(id);
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
