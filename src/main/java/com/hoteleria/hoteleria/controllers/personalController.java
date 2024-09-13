package com.hoteleria.hoteleria.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hoteleria.hoteleria.dtos.hotelDto;
import com.hoteleria.hoteleria.dtos.puestoDto;
import com.hoteleria.hoteleria.dtos.staffDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.hotel;
import com.hoteleria.hoteleria.models.personal;
import com.hoteleria.hoteleria.models.puesto;
import com.hoteleria.hoteleria.services.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:8100" }, methods = { RequestMethod.POST,
        RequestMethod.GET,
        RequestMethod.DELETE,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class personalController {

    @Autowired
    private personalService personalService;

    @Autowired
    private puestoService rolService;

    @Autowired
    private hotelService hotelService;

    @GetMapping("/personals")
    public ResponseEntity<responseHelper<List<staffDto>>> getAllPersonal() {
        return handleResponse(() -> personalService.getAllPersonal());
    }

    @PostMapping("/personals/email")
    public ResponseEntity<responseHelper<staffDto>> findByEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");

        try {
            staffDto personal = personalService.findByEmail(email).orElse(null);
            if (personal == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado does not exist"));
            }

            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, personal, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PostMapping("/personals/uuid")
    public ResponseEntity<responseHelper<staffDto>> findById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));
        staffDto personal = personalService.findById(id).orElse(null);

        try {
            if (personal == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado does not exist"));
            }

            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, personal, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PostMapping("/personals/phone")
    public ResponseEntity<responseHelper<staffDto>> findByTelefono(@RequestBody Map<String, String> requestBody) {
        String telefono = requestBody.get("phone");

        try {
            staffDto personal = personalService.findByTelefono(telefono);
            if (personal == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado does not exist"));
            }

            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, personal, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PostMapping(value = "/personals", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<responseHelper<Object>> createPersonal(@RequestBody personal personal) {
        try {
            // Verificar si el email ya existe
            staffDto exiStaffDto = personalService.findByEmail(personal.getEmail()).orElse(null);
            if (exiStaffDto != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado already exists"));
            }

            // Verificar si el rol y el hotel existen
            puestoDto rolOpt = rolService.findById(personal.getRol().getId());
            hotelDto hotelOpt = hotelService.findById(personal.getHotel().getId());

            if (rolOpt == null || hotelOpt == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null,
                                "Rol or Hotel does not exist"));
            }

            puesto rolEntity = personalService.convertToPuesto(rolOpt);
            hotel hotelEntity = personalService.convertToHotel(hotelOpt);

            // Asignar al objeto personal
            personal.setRol(rolEntity);
            personal.setHotel(hotelEntity);

            // Guardar el personal
            personal savedPersonal = personalService.save(personal);
            return ResponseEntity.ok(new responseHelper<>("Success", HttpStatus.OK, savedPersonal, null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping("/personals")
    public ResponseEntity<responseHelper<Object>> updatePersonal(@RequestBody staffDto staff) {
        staffDto exiStaffDto = personalService.findById(staff.getId()).orElse(null);
        try {

            if (exiStaffDto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado does not exist"));
            }

            return ResponseEntity
                    .ok(new responseHelper<>("Success", HttpStatus.OK, personalService.updatePersonal(staff), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e));
        }
    }

    @DeleteMapping("/personals")
    public ResponseEntity<responseHelper<Object>> deleteById(@RequestBody Map<String, String> requestBody) {
        UUID id = UUID.fromString(requestBody.get("uuid"));
        staffDto exiStaffDto = personalService.findById(id).orElse(null);
        try {
            if (exiStaffDto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Empleado does not exist"));
            }

            return ResponseEntity
                    .ok(new responseHelper<>("Success", HttpStatus.OK, personalService.deleteById(id), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
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
