package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.facturaDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.factura;
import com.hoteleria.hoteleria.services.facturaService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173", "https://mango-smoke-084edd70f.5.azurestaticapps.net" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
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
        return handleResponse(() -> {
            facturaDto factura = facturaService.getById(id);
            if (factura == null) {
                throw new ResourceNotFoundException("Factura does not exist");
            }
            return factura;
        });
    }

    @PostMapping("/facturas") // create factura
    public ResponseEntity<responseHelper<facturaDto>> createFactura(@Valid @RequestBody factura factura) {
        return handleResponse(() -> {
            facturaDto createdFactura = facturaService.save(factura);
            return createdFactura;
        });
    }

    @PatchMapping("/facturas") // update factura
    public ResponseEntity<responseHelper<facturaDto>> updateFactura(@Valid @RequestBody facturaDto factura) {
        return handleResponse(() -> {
            facturaDto facturaExist = facturaService.getById(factura.getId());

            if (facturaExist == null) {
                throw new ResourceNotFoundException("Factura does not exist");
            }

            facturaDto updatedFactura = facturaService.update(factura);

            return updatedFactura;
        });
    }

    @DeleteMapping("/facturas") // delete factura
    public ResponseEntity<responseHelper<Boolean>> deleteFactura(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        return handleResponse(() -> {
            facturaDto factura = facturaService.getById(id);
            if (factura == null) {
                throw new ResourceNotFoundException("Factura does not exist");
            }
            facturaService.delete(id);
            return true;
        });
    }

    // helper method to handle responses
    private <T> ResponseEntity<responseHelper<T>> handleResponse(Supplier<T> action) {
        try {
            T result = action.get();
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, result, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new responseHelper<>("Error", HttpStatus.NOT_FOUND, null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }
}
