package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.facturaDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.factura;
import com.hoteleria.hoteleria.services.facturaService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:8100" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class facturaController {
    @Autowired
    private facturaService facturaService;

    @GetMapping("/facturas") // get all facturas
    public ResponseEntity<responseHelper<List<facturaDto>>> getAllFacturas() {
        return handleResponse(() -> facturaService.getAll());
    }

    @GetMapping("/facturas/id") // get factura by id
    public ResponseEntity<responseHelper<facturaDto>> getFacturaById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        facturaDto factura = facturaService.getById(id);
        if (factura == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Factura does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, factura, null));
    }

    @PostMapping("/facturas") // create factura
    public ResponseEntity<responseHelper<facturaDto>> createFactura(@Valid @RequestBody factura factura) {
        try {
            facturaDto newFactura = facturaService.save(factura);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, newFactura, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping("/facturas") // update factura
    public ResponseEntity<responseHelper<facturaDto>> updateFactura(@Valid @RequestBody facturaDto factura) {
        try {
            facturaDto newFactura = facturaService.update(factura);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, newFactura, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e));
        }
    }

    @DeleteMapping("/facturas") // delete factura
    public ResponseEntity<responseHelper<Boolean>> deleteFactura(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        facturaDto factura = facturaService.getById(id);
        try {
            if (factura == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, false,
                                "Factura does not exist"));
            }
            facturaService.delete(id);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, false, e.getMessage()));
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
