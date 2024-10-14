package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.hotelDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.hotel;
import com.hoteleria.hoteleria.services.hotelService;

import jakarta.validation.Valid;

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
        return handleServiceResponse(() -> hotelService.findAll());
    }

    @GetMapping("/hotels/name") // get hotel by name
    public ResponseEntity<responseHelper<hotelDto>> getHotelbyNombre(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");

        return handleServiceResponse(() -> {
            hotelDto hotel = hotelService.findByNombre(name);
            if (hotel == null) {
                throw new RuntimeException("Hotel does not exist");
            }
            return hotel;
        });
    }

    @GetMapping("/hotels/uuid") // get hotel by id
    public ResponseEntity<responseHelper<Object>> getHotelbyId(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));

        return handleServiceResponse(() -> {
            hotelDto hotel = hotelService.findById(id);
            if (hotel == null) {
                throw new RuntimeException("Hotel does not exist");
            }
            return hotel;
        });
    }

    @PostMapping("/hotels") // create a new hotel
    public ResponseEntity<responseHelper<Object>> createHotel(@Valid @RequestBody hotel hotel) {
        return handleServiceResponse(() -> {
            hotelDto hotelExist = hotelService.findByNombre(hotel.getName());
            if (hotelExist != null) {
                throw new RuntimeException("Hotel already exists");
            }
            return hotelService.save(hotel);
        });
    }

    @PutMapping("/hotels") // update a hotel
    public ResponseEntity<responseHelper<hotelDto>> updateHotel(@RequestBody hotelDto hotel) {
        return handleServiceResponse(() -> {
            hotelDto hotelExist = hotelService.findById(hotel.getId());
            if (hotelExist == null) {
                throw new RuntimeException("Hotel does not exist");
            }
            return hotelService.update(hotel);
        });
    }

    @DeleteMapping("/hotels") // delete a hotel
    public ResponseEntity<responseHelper<Object>> deleteHotel(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));

        return handleServiceResponse(() -> {
            hotelDto hotel = hotelService.findById(id);
            if (hotel == null) {
                throw new RuntimeException("Hotel does not exist");
            }
            hotelService.deleteById(id);
            return true;
        });
    }

    // Helper method to handle response and error handling
    private <T> ResponseEntity<responseHelper<T>> handleServiceResponse(Supplier<T> action) {
        try {
            T result = action.get();
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, result, null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }
}
