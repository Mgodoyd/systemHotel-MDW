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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoteleria.hoteleria.dtos.puestoDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.puesto;
import com.hoteleria.hoteleria.services.*;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:8100" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class puestoController {

    @Autowired
    private puestoService puestoService;

    @GetMapping("/puestos") // get all puestos
    public ResponseEntity<responseHelper<List<puestoDto>>> getAllPuestos() {
        return handleResponse(() -> puestoService.findAll());
    }

    @GetMapping("/puestos/name") // get puesto by nombre
    public ResponseEntity<responseHelper<puestoDto>> getPuestobyNombre(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");

        try {
            puestoDto puesto = puestoService.findByName(name);
            if (puesto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Puesto does not exist"));
            }

            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, puesto, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @GetMapping("/puestos/uuid") // get puesto by id
    public ResponseEntity<responseHelper<Object>> getPuestobyId(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));

        try {
            puestoDto puesto = puestoService.findById(id);
            if (puesto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Puesto does not exist"));
            }

            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, puesto, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PostMapping("/puestos")
    public ResponseEntity<responseHelper<Object>> createPuesto(@Valid @RequestBody puesto puesto) {
        puestoDto existPuesto = puestoService.findByName(puesto.getName());
        try {
            if (existPuesto != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Puesto already exists"));
            }

            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, puestoService.save(puesto), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, e.getMessage()));
        }
    }

    @PutMapping("/puestos") // update a puesto
    public ResponseEntity<responseHelper<puestoDto>> updatePuesto(@RequestBody puestoDto puesto) {
        try {
            puestoDto updatedPuesto = puestoService.update(puesto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, updatedPuesto, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, e.getMessage()));
        }
    }

    @DeleteMapping("/puestos") // delete a puesto
    public ResponseEntity<responseHelper<Object>> deletePuesto(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));

        try {
            puestoService.deleteById(id);
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
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }
}
