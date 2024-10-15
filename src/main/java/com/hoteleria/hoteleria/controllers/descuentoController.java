package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.descuentoDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.descuentoService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class descuentoController {

    @Autowired
    private descuentoService descuentoService;

    @GetMapping("/descuentos") // get all descuentos
    public ResponseEntity<responseHelper<List<descuentoDto>>> getAllDescuentos() {
        return handleResponse(() -> descuentoService.getDescuentos());
    }

    @GetMapping("/descuentos/id") // get descuento by id
    public ResponseEntity<responseHelper<descuentoDto>> getDescuentoById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        return handleResponse(() -> {
            descuentoDto descuento = descuentoService.getDescuento(id);
            if (descuento == null) {
                throw new IllegalArgumentException("Descuento does not exist");
            }
            return descuento;
        });
    }

    @GetMapping("/descuentos/codigo") // get descuento by code
    public ResponseEntity<responseHelper<descuentoDto>> getDescuentoByCode(
            @RequestBody Map<String, String> requestBody) {
        String codigo = requestBody.get("code");

        return handleResponse(() -> {
            descuentoDto descuento = descuentoService.getDescuentoByCode(codigo);
            if (descuento == null) {
                throw new IllegalArgumentException("Descuento does not exist");
            }
            return descuento;
        });
    }

    @PostMapping("/descuentos") // create descuento
    public ResponseEntity<responseHelper<descuentoDto>> createDescuento(@RequestBody descuentoDto descuentoDto) {
        descuentoDto descuentoExist = descuentoService.getDescuentoByCode(descuentoDto.getCodigo());
        return handleResponse(() -> {
            if (descuentoExist != null) {
                throw new IllegalArgumentException("Descuento already exists");
            }
            return descuentoService.save(descuentoDto);
        });
    }

    @PatchMapping("/descuentos") // update descuento
    public ResponseEntity<responseHelper<descuentoDto>> updateDescuento(@RequestBody descuentoDto descuentoDto) {
        descuentoDto descuentoExist = descuentoService.getDescuento(descuentoDto.getId());

        return handleResponse(() -> {
            if (descuentoExist == null) {
                throw new IllegalArgumentException("Descuento does not exist");
            }
            return descuentoService.update(descuentoDto);
        });
    }

    @DeleteMapping("/descuentos") // delete descuento
    public ResponseEntity<responseHelper<Object>> deleteDescuento(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        return handleResponse(() -> {
            descuentoDto descuento = descuentoService.getDescuento(id);
            if (descuento == null) {
                throw new IllegalArgumentException("Descuento does not exist");
            }
            descuentoService.deleteDescuento(id);
            return true;
        });

    }

    // Handles the response of the request
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
