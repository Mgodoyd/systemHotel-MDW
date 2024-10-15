package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.serviciosDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.servicioService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class serviciosController {
    @Autowired
    private servicioService servicioService;

    @GetMapping("/servicios") // get all servicios
    public ResponseEntity<responseHelper<List<serviciosDto>>> getAllServicios() {
        return handleResponse(() -> servicioService.getServicios());
    }

    @GetMapping("/servicios/id") // Get servicio by id
    public ResponseEntity<responseHelper<serviciosDto>> getServicioById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        return handleResponse(() -> {
            serviciosDto servicio = servicioService.getServicio(id);
            if (servicio == null) {
                throw new IllegalArgumentException("Servicio does not exist");
            }
            return servicio;
        });
    }

    @GetMapping("/servicios/nombre") // Get servicio by nombre
    public ResponseEntity<responseHelper<serviciosDto>> getServicioByNombre(
            @RequestBody Map<String, String> requestBody) {
        String nombre = requestBody.get("nombre");
        return handleResponse(() -> {
            serviciosDto servicio = servicioService.getServicioByNombre(nombre);
            if (servicio == null) {
                throw new IllegalArgumentException("Servicio does not exist");
            }
            return servicio;
        });
    }

    @PostMapping("/servicios") // Save servicio
    public ResponseEntity<responseHelper<serviciosDto>> saveServicio(@RequestBody serviciosDto servicio) {
        return handleResponse(() -> {
            serviciosDto servicioExist = servicioService.getServicioByNombre(servicio.getNombre());
            if (servicioExist != null) {
                throw new IllegalArgumentException("Servicio already exists");
            }
            return servicioService.save(servicio);
        });
    }

    @PatchMapping("/servicios") // Update servicio
    public ResponseEntity<responseHelper<serviciosDto>> updateServicio(@RequestBody serviciosDto servicio) {
        return handleResponse(() -> {
            serviciosDto servicioExist = servicioService.getServicio(servicio.getId());
            if (servicioExist == null) {
                throw new IllegalArgumentException("Servicio does not exist");
            }
            return servicioService.update(servicio);
        });
    }

    @DeleteMapping("/servicios") // Delete servicio
    public ResponseEntity<responseHelper<Boolean>> deleteServicio(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        return handleResponse(() -> {
            serviciosDto servicio = servicioService.getServicio(id);
            if (servicio == null) {
                throw new IllegalArgumentException("Servicio does not exist");
            }
            servicioService.delete(id);
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
