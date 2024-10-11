package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.parqueoDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.parqueoService;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:8100" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
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

        parqueoDto parqueo = parqueoService.getParqueo(id);
        if (parqueo == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Parqueo does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, parqueo, null));
    }

    @PostMapping("/parqueos") // create parqueo
    public ResponseEntity<responseHelper<parqueoDto>> createParqueo(@RequestBody parqueoDto parqueoDto) {
        try {
            parqueoDto parqueo = parqueoService.save(parqueoDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, parqueo, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping("/parqueos") // update parqueo
    public ResponseEntity<responseHelper<parqueoDto>> updateParqueo(@RequestBody parqueoDto parqueoDto) {
        try {
            parqueoDto parqueo = parqueoService.update(parqueoDto);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, parqueo, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @DeleteMapping("/parqueos") // delete parqueo
    public ResponseEntity<responseHelper<Boolean>> deleteParqueo(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("id"));

        boolean deleted = parqueoService.delete(id);

        try {
            if (deleted) {
                return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, true, null));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, false,
                                "Parqueo does not exist"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, false, e));
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
