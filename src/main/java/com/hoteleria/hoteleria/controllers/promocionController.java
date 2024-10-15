package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.promocionDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.promocionService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
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

        return handleResponse(() -> {
            promocionDto promocion = promocionService.getPromocion(id);
            if (promocion == null) {
                throw new IllegalArgumentException("Promocion does not exist");
            }
            return promocion;
        });
    }

    @PostMapping("/promociones") // create promocion
    public ResponseEntity<responseHelper<promocionDto>> createPromocion(@RequestBody promocionDto promocionDto) {
        return handleResponse(() -> promocionService.save(promocionDto));
    }

    @PatchMapping("/promociones") // update promocion
    public ResponseEntity<responseHelper<promocionDto>> updatePromocion(@RequestBody promocionDto promocionDto) {
        promocionDto promocionExist = promocionService.getPromocion(promocionDto.getId());

        return handleResponse(() -> {
            if (promocionExist == null) {
                throw new IllegalArgumentException("Promocion does not exist");
            }
            return promocionService.update(promocionDto);
        });
    }

    @DeleteMapping("/promociones") // delete promocion
    public ResponseEntity<responseHelper<Object>> deletePromocion(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        return handleResponse(() -> {
            promocionDto promocion = promocionService.getPromocion(id);
            if (promocion == null) {
                throw new IllegalArgumentException("Promocion does not exist");
            }
            promocionService.deletePromocion(id);
            return true;
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
