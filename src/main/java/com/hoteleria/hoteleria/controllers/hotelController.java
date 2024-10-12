package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoteleria.hoteleria.dtos.hotelDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.hotel;
import com.hoteleria.hoteleria.services.hotelService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class hotelController {

    @Autowired
    private hotelService hotelService;

    @GetMapping("/hotels") // get all hotels
    public ResponseEntity<responseHelper<List<hotelDto>>> getAllHotels() {
        return handleResponse(() -> hotelService.findAll());
    }

    @GetMapping("/hotels/name") // get hotel by nombre
    public ResponseEntity<responseHelper<hotelDto>> getHotelbyNombre(@RequestBody Map<String, String> requestBody) {
        String nmae = requestBody.get("name");

        hotelDto hotel = hotelService.findByNombre(nmae);
        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Hotel does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, hotel, null));
    }

    @GetMapping("/hotels/uuid") // get hotel by id
    public ResponseEntity<responseHelper<Object>> getHotelbyId(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));

        hotelDto hotel = hotelService.findById(id);
        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            "Hotel does not exist"));
        }

        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, hotel, null));
    }

    @PostMapping("/hotels") // create a new hotel
    public ResponseEntity<responseHelper<Object>> createHotel(@Valid @RequestBody hotel hotel) {
        hotelDto hotelExist = hotelService.findByNombre(hotel.getName());

        try {
            if (hotelExist != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Hotel already exists"));
            }

            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, hotelService.save(hotel), null));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, e.getMessage()));
        }
    }

    @PutMapping("/hotels") // update a hotel
    public ResponseEntity<responseHelper<hotelDto>> updateHotel(@RequestBody hotelDto hotel) {
        hotelDto hotelExist = hotelService.findById(hotel.getId());

        try {
            if (hotelExist == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Hotel does not exist"));
            }

            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, hotelService.update(hotel), null));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, e.getMessage()));
        }
    }

    @DeleteMapping("/hotels") // delete a hotel
    public ResponseEntity<responseHelper<Object>> deleteHotel(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));

        hotelDto hotel = hotelService.findById(id);
        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Hotel does not exist"));
        }

        hotelService.deleteById(id);
        return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, null, null));
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
