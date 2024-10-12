package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.descuentoDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.descuentoService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class descuentoController {

    @Autowired
    private descuentoService descuentoService;

    @GetMapping("/descuentos") // get all descuentos
    public ResponseEntity<responseHelper<List<descuentoDto>>> getAllDescuentos() {
        return handleResponse(() -> descuentoService.getDescuentos());
    }

    @GetMapping("/descuentos/id") // get descuento by id
    public ResponseEntity<responseHelper<?>> getDescuentoById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        descuentoDto descuento = descuentoService.getDescuento(id);
        try {
            if (descuento == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null,
                                "Descuento does not exist"));
            }
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, descuento, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @GetMapping("/descuentos/codigo") // get descuento by code
    public ResponseEntity<responseHelper<?>> getDescuentoByCode(@RequestBody Map<String, String> requestBody) {
        String codigo = requestBody.get("code");

        descuentoDto descuento = descuentoService.getDescuentoByCode(codigo);
        try {
            if (descuento == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null,
                                "Descuento does not exist"));
            }
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, descuento, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PostMapping("/descuentos") // create descuento
    public ResponseEntity<responseHelper<descuentoDto>> createDescuento(@RequestBody descuentoDto descuentoDto) {
        try {
            descuentoDto descuento = descuentoService.save(descuentoDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, descuento, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping("/descuentos") // update descuento
    public ResponseEntity<responseHelper<descuentoDto>> updateDescuento(@RequestBody descuentoDto descuentoDto) {
        try {
            descuentoDto descuento = descuentoService.update(descuentoDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, descuento, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @DeleteMapping("/descuentos") // delete descuento
    public ResponseEntity<responseHelper<Object>> deleteDescuento(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        descuentoDto descuento = descuentoService.getDescuento(id);
        try {
            if (descuento == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, false,
                                "Descuento does not exist"));
            }
            descuentoService.deleteDescuento(id);
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
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }
}
