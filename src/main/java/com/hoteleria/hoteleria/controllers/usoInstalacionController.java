package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.usoInstalacionDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.usoInstalacionService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:8100" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class usoInstalacionController {
    @Autowired
    private usoInstalacionService usoInstalacionService;

    @GetMapping("/usoInstalaciones") // get all usoInstalaciones
    public ResponseEntity<responseHelper<List<usoInstalacionDto>>> getAllUsoInstalaciones() {
        return handleResponse(() -> usoInstalacionService.getUsoInstalaciones());
    }

    @GetMapping("/usoInstalaciones/id") // get usoInstalacion by id
    public ResponseEntity<responseHelper<usoInstalacionDto>> getUsoInstalacionById(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        usoInstalacionDto usoInstalacion = usoInstalacionService.getUsoInstalacion(id);
        if (usoInstalacion == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "UsoInstalacion does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, usoInstalacion, null));
    }

    @PostMapping("/usoInstalaciones") // create usoInstalacion
    public ResponseEntity<responseHelper<usoInstalacionDto>> createUsoInstalacion(
            @RequestBody usoInstalacionDto usoInstalacionDto) {
        try {
            usoInstalacionDto usoInstalacion = usoInstalacionService.save(usoInstalacionDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, usoInstalacion, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping("/usoInstalaciones") // update usoInstalacion
    public ResponseEntity<responseHelper<usoInstalacionDto>> updateUsoInstalacion(
            @RequestBody usoInstalacionDto usoInstalacionDto) {
        try {
            usoInstalacionDto usoInstalacion = usoInstalacionService.save(usoInstalacionDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, usoInstalacion, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @DeleteMapping("/usoInstalaciones") // delete usoInstalacion
    public ResponseEntity<responseHelper<Boolean>> deleteUsoInstalacion(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        usoInstalacionDto usoInstalacion = usoInstalacionService.getUsoInstalacion(id);

        try {
            if (usoInstalacion == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, false,
                                "UsoInstalacion does not exist"));
            }

            boolean deleted = usoInstalacionService.delete(id);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, deleted, null));
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
