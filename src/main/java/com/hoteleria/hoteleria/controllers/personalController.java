package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.hoteleria.hoteleria.dtos.staffDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.services.*;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173","https://mango-smoke-084edd70f.5.azurestaticapps.net" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PATCH }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class personalController {

    @Autowired
    private personalService personalService;

    @GetMapping("/personals") // get all personals
    public ResponseEntity<responseHelper<List<staffDto>>> getAllPersonal() {
        return handleResponse(() -> personalService.getAllPersonal());
    }

    @PostMapping("/personals/email") // find a personal by email
    public ResponseEntity<responseHelper<staffDto>> findByEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        return handleResponse(() -> {
            staffDto personal = personalService.findByEmail(email).orElse(null);
            if (personal == null) {
                throw new RuntimeException("Empleado does not exist");
            }
            return personal;
        });
    }

    @PostMapping("/personals/uuid") // find a personal by id
    public ResponseEntity<responseHelper<staffDto>> findById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));
        return handleResponse(() -> {
            staffDto personal = personalService.findById(id).orElse(null);
            if (personal == null) {
                throw new RuntimeException("Empleado does not exist");
            }
            return personal;
        });
    }

    @PostMapping("/personals/phone") // find a personal by phone
    public ResponseEntity<responseHelper<staffDto>> findByTelefono(@RequestBody Map<String, String> requestBody) {
        String telefono = requestBody.get("phone");
        return handleResponse(() -> {
            staffDto personal = personalService.findByTelefono(telefono).orElse(null);
            if (personal == null) {
                throw new RuntimeException("Empleado does not exist");
            }
            return personal;
        });
    }

    @PostMapping("/personals") // create a personal
    public ResponseEntity<responseHelper<staffDto>> createPersonal(@RequestBody staffDto personal) {
        return handleResponse(() -> {
            staffDto exiStaffDto = personalService.findByEmail(personal.getEmail()).orElse(null);
            if (exiStaffDto != null) {
                throw new RuntimeException("Empleado already exists");
            }
            return personalService.save(personal);
        });
    }

    @PatchMapping("/personals") // update a personal
    public ResponseEntity<responseHelper<Object>> updatePersonal(@RequestBody staffDto staff) {
        return handleResponse(() -> {
            staffDto exiStaffDto = personalService.findById(staff.getId()).orElse(null);
            if (exiStaffDto == null) {
                throw new RuntimeException("Empleado does not exist");
            }
            return personalService.updatePersonal(staff);
        });
    }

    @DeleteMapping("/personals") // delete a personal
    public ResponseEntity<responseHelper<Object>> deleteById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));
        return handleResponse(() -> {
            staffDto exiStaffDto = personalService.findById(id).orElse(null);
            if (exiStaffDto == null) {
                throw new RuntimeException("Empleado does not exist");
            }
            return personalService.deleteById(id);
        });
    }

    // Helper method to handle responses
    private <T> ResponseEntity<responseHelper<T>> handleResponse(Supplier<T> action) {
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
