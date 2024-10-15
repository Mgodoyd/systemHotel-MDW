package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.parqueoDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.parqueoService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class parqueoController {

    @Autowired
    private parqueoService parqueoService;

    @GetMapping("/parqueos") // get all parqueos
    public ResponseEntity<responseHelper<List<parqueoDto>>> getAllParqueos() {
        return handleResponse(() -> parqueoService.getParqueos());
    }

    @GetMapping("/parqueos/id") // get parqueo by id
    public ResponseEntity<responseHelper<parqueoDto>> getParqueoById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));
        return handleResponse(() -> {
            parqueoDto parqueo = parqueoService.getParqueo(id);
            if (parqueo == null) {
                throw new IllegalArgumentException("Parqueo does not exist");
            }
            return parqueo;
        });
    }

    @PostMapping("/parqueos") // create parqueo
    public ResponseEntity<responseHelper<parqueoDto>> createParqueo(@RequestBody parqueoDto parqueoDto) {
        return handleResponse(() -> parqueoService.save(parqueoDto));
    }

    @PatchMapping("/parqueos") // update parqueo
    public ResponseEntity<responseHelper<parqueoDto>> updateParqueo(@RequestBody parqueoDto parqueoDto) {
        parqueoDto parqueoExist = parqueoService.getParqueo(parqueoDto.getId());
        return handleResponse(() -> {
            if (parqueoExist == null) {
                throw new IllegalArgumentException("Parqueo does not exist");
            }
            return parqueoService.save(parqueoDto);
        });
    }

    @DeleteMapping("/parqueos") // delete parqueo
    public ResponseEntity<responseHelper<Boolean>> deleteParqueo(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        return handleResponse(() -> {
            parqueoDto parqueo = parqueoService.getParqueo(id);
            if (parqueo == null) {
                throw new IllegalArgumentException("Parqueo does not exist");
            }
            parqueoService.delete(id);
            return true;
        });
    }

    // Helper method to handle responses
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
