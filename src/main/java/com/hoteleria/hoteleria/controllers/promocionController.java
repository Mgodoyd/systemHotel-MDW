package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoteleria.hoteleria.dtos.promocionDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.promocionService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:8100" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class promocionController {

    @Autowired
    private promocionService promocionService;

    @GetMapping("/promociones") // get all promociones
    public ResponseEntity<responseHelper<List<promocionDto>>> getAllPromociones() {
        return handleResponse(() -> promocionService.getPromociones());

    }

    @GetMapping("/promociones/id") // get promocion by id
    public ResponseEntity<responseHelper<promocionDto>> getPromocionById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        promocionDto promocion = promocionService.getPromocion(id);
        try {
            if (promocion == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null,
                                "Promocion does not exist"));
            }
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, promocion, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PostMapping("/promociones") // create promocion
    public ResponseEntity<responseHelper<promocionDto>> createPromocion(@RequestBody promocionDto promocionDto) {
        try {
            promocionDto promocion = promocionService.save(promocionDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, promocion, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping("/promociones") // update promocion
    public ResponseEntity<responseHelper<promocionDto>> updatePromocion(@RequestBody promocionDto promocionDto) {
        try {
            promocionDto promocion = promocionService.save(promocionDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, promocion, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @DeleteMapping("/promociones") // delete promocion
    public ResponseEntity<responseHelper<Object>> deletePromocion(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        promocionDto promocion = promocionService.getPromocion(id);
        try {
            if (promocion == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null,
                                "Promocion does not exist"));
            }
            promocionService.deletePromocion(id);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    private <T> ResponseEntity<responseHelper<T>> handleResponse(Supplier<T> action) {
        try {
            T result = action.get();
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, result, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }
}
