package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.personal;
import com.hoteleria.hoteleria.services.personalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:8100",
        "https://jmghcf68-8100.use2.devtunnels.ms/" }, methods = { RequestMethod.POST, RequestMethod.GET,
                RequestMethod.DELETE,
                RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class personalController {

    @Autowired
    private personalService personalService;

    @GetMapping("/personals") // get all personals
    public ResponseEntity<responseHelper<List<personal>>> getAllPersonals() {
        return handleResponse(() -> personalService.getPersonals());
    }

    @PostMapping("/personals/email") // create a new personal
    public ResponseEntity<responseHelper<Object>> getPersonalbyEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");

        personal personal = personalService.getPersonal(email);
        if (personal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado does not exist"));
        }

        return handleResponse(() -> {
            return personalService.getPersonal(email);
        });
    }

    @PostMapping("/personals/telefono") // get personal by telefono
    public ResponseEntity<responseHelper<Object>> getPersonalbyTelefono(@RequestBody Map<String, String> requestBody) {
        String telefono = requestBody.get("telefono");

        personal personal = personalService.getPersonalByTelefono(telefono);
        if (personal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado does not exist"));
        }

        return handleResponse(() -> {
            return personalService.getPersonalByTelefono(telefono);
        });
    }

    @PostMapping("/personals") // create a new personal
    public ResponseEntity<responseHelper<Object>> createPersonal(@RequestBody personal personal) {
        personal personalExist = personalService.getPersonalByTelefono(personal.getTelefono());
        if (personalExist != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado already exists"));
        }

        return handleResponse(() -> {
            return personalService.createPersonal(personal);
        });
    }

    @PutMapping("/personals/update") // update a personal
    public ResponseEntity<responseHelper<Object>> updatePersonal(@RequestBody personal personal) {
        personal personalExist = personalService.getPersonalByTelefono(personal.getTelefono());
        if (personalExist == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado does not exist"));
        }

        return handleResponse(() -> {
            return personalService.updatePersonal(personal);
        });
    }

    @PutMapping("/personals/delete") // delete a personal
    public ResponseEntity<responseHelper<Object>> deletePersonal(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");

        personal personal = personalService.getPersonal(email);
        if (personal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado does not exist"));
        }

        return handleResponse(() -> {
            return personalService.deletePersonal(email);
        });
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
