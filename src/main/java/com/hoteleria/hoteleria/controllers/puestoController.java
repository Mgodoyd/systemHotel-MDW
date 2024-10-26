package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.puestoDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.puesto;
import com.hoteleria.hoteleria.services.puestoService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173",
        "https://mango-smoke-084edd70f.5.azurestaticapps.net" }, methods = { RequestMethod.POST,
                RequestMethod.GET,
                RequestMethod.DELETE,
                RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
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
        return handleResponse(() -> {
            puestoDto puesto = puestoService.findByName(name);
            if (puesto == null) {
                throw new IllegalArgumentException("Puesto does not exist");
            }
            return puesto;
        });
    }

    @GetMapping("/puestos/uuid") // get puesto by id
    public ResponseEntity<responseHelper<Object>> getPuestobyId(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));
        return handleResponse(() -> {
            puestoDto puesto = puestoService.findById(id);
            if (puesto == null) {
                throw new IllegalArgumentException("Puesto does not exist");
            }
            return puesto;
        });
    }

    @PostMapping("/puestos")
    public ResponseEntity<responseHelper<Object>> createPuesto(@Valid @RequestBody puesto puesto) {
        return handleResponse(() -> {
            puestoDto existPuesto = puestoService.findByName(puesto.getName());
            if (existPuesto != null) {
                throw new IllegalArgumentException("Puesto already exists");
            }
            return puestoService.save(puesto);
        });
    }

    @PatchMapping("/puestos") // update a puesto
    public ResponseEntity<responseHelper<puestoDto>> updatePuesto(@RequestBody puestoDto puesto) {
        return handleResponse(() -> {
            puestoDto existPuesto = puestoService.findById(puesto.getId());
            if (existPuesto == null) {
                throw new IllegalArgumentException("Puesto does not exist");
            }
            return puestoService.update(puesto);
        });
    }

    @DeleteMapping("/puestos") // delete a puesto
    public ResponseEntity<responseHelper<Object>> deletePuesto(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));
        return handleResponse(() -> {
            puestoDto puesto = puestoService.findById(id);
            if (puesto == null) {
                throw new IllegalArgumentException("Puesto does not exist");
            }
            puestoService.delete(id);
            return true;
        });
    }

    /**
     * Handles the response for a given action, wrapping the result in a
     * ResponseEntity.
     * 
     * @param <T>    The type of the result produced by the action.
     * @param action A Supplier that performs the action and returns a result.
     * @return A ResponseEntity containing a responseHelper object with the result
     *         of the action.
     *         If the action is successful, the status is HttpStatus.OK.
     *         If an IllegalArgumentException is thrown, the status is
     *         HttpStatus.BAD_REQUEST.
     *         For any other exceptions, the status is
     *         HttpStatus.INTERNAL_SERVER_ERROR.
     */
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
