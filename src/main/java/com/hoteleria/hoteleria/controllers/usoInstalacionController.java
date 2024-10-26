package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.usoInstalacionDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.usoInstalacionService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173",
        "https://mango-smoke-084edd70f.5.azurestaticapps.net" }, methods = { RequestMethod.POST,
                RequestMethod.GET,
                RequestMethod.DELETE,
                RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
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

        return handleResponse(() -> {
            usoInstalacionDto usoInstalacion = usoInstalacionService.getUsoInstalacion(id);
            if (usoInstalacion == null) {
                throw new IllegalArgumentException("UsoInstalacion does not exist");
            }
            return usoInstalacion;
        });
    }

    @PostMapping("/usoInstalaciones") // create usoInstalacion
    public ResponseEntity<responseHelper<usoInstalacionDto>> createUsoInstalacion(
            @RequestBody usoInstalacionDto usoInstalacionDto) {
        return handleResponse(() -> usoInstalacionService.save(usoInstalacionDto));
    }

    @PatchMapping("/usoInstalaciones") // update usoInstalacion
    public ResponseEntity<responseHelper<usoInstalacionDto>> updateUsoInstalacion(
            @RequestBody usoInstalacionDto usoInstalacionDto) {
        usoInstalacionDto usoInstalacionExist = usoInstalacionService.getUsoInstalacion(usoInstalacionDto.getId());

        return handleResponse(() -> {
            if (usoInstalacionExist == null) {
                throw new IllegalArgumentException("UsoInstalacion does not exist");
            }
            return usoInstalacionService.save(usoInstalacionDto);
        });
    }

    @DeleteMapping("/usoInstalaciones") // delete usoInstalacion
    public ResponseEntity<responseHelper<Boolean>> deleteUsoInstalacion(
            @RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        return handleResponse(() -> {
            usoInstalacionDto usoInstalacion = usoInstalacionService.getUsoInstalacion(id);
            if (usoInstalacion == null) {
                throw new IllegalArgumentException("UsoInstalacion does not exist");
            }
            return usoInstalacionService.delete(id);
        });
    }

    // Helper method to handle responses
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
