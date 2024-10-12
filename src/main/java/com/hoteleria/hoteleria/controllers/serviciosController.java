package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.serviciosDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.servicioService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class serviciosController {
    @Autowired
    private servicioService servicioService;

    @GetMapping("/servicios") // get all servicios
    public ResponseEntity<responseHelper<List<serviciosDto>>> getAllServicios() {
        return handleResponse(() -> servicioService.getServicios());
    }

    @GetMapping("/servicios/id") // get servicio by id
    public ResponseEntity<responseHelper<serviciosDto>> getServicioById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        serviciosDto servicio = servicioService.getServicio(id);
        if (servicio == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Servicio does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, servicio, null));
    }

    @GetMapping("/servicios/nombre") // get servicio by nombre
    public ResponseEntity<responseHelper<serviciosDto>> getServicioByNombre(
            @RequestBody Map<String, String> requestBody) {
        String nombre = requestBody.get("nombre");

        serviciosDto servicio = servicioService.getServicioByNombre(nombre);
        if (servicio == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Servicio does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, servicio, null));
    }

    @PostMapping("/servicios") // save servicio
    public ResponseEntity<responseHelper<serviciosDto>> saveServicio(@RequestBody serviciosDto servicio) {
        try {
            serviciosDto newServicio = servicioService.save(servicio);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, newServicio, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping("/servicios") // update servicio
    public ResponseEntity<responseHelper<serviciosDto>> updateServicio(@RequestBody serviciosDto servicio) {
        try {
            servicioService.update(servicio);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, servicio, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @DeleteMapping("/servicios") // delete servicio
    public ResponseEntity<responseHelper<Boolean>> deleteServicio(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        serviciosDto servicio = servicioService.getServicio(id);

        try {
            if (servicio == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, false,
                                "Servicio does not exist"));
            }

            servicioService.delete(id);
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
