package com.hoteleria.hoteleria.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoteleria.hoteleria.dtos.clienteDto;
import com.hoteleria.hoteleria.dtos.staffDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.personal;
import com.hoteleria.hoteleria.services.clienteService;
import com.hoteleria.hoteleria.services.personalService;
import com.hoteleria.hoteleria.services.auth.AuthenticationRequest;
import com.hoteleria.hoteleria.services.auth.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:5173" }, methods = { RequestMethod.POST,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class loginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private personalService userService;

    @Autowired
    private clienteService clienteService;

    // public loginController(AuthenticationService authenticationService) {
    // this.authenticationService = authenticationService;
    // }

    @PostMapping("/register") // Register a new user
    public ResponseEntity<responseHelper<Object>> register(@Valid @RequestBody personal user) {
        try {
            // Convertir el objeto personal a JSON y imprimirlo en consola
            ObjectMapper objectMapper = new ObjectMapper();
            String userJson = objectMapper.writeValueAsString(user);
            System.out.println("Datos del usuario en JSON: " + userJson);

            staffDto existingEmail = userService.findByEmail(user.getEmail()).orElse(null);
            clienteDto existingNit = clienteService.getByEmail(user.getEmail());

            if (existingEmail != null || existingNit != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Email already exists"));
            }

            // staffDto existingUsers = userService.findByTelefono(user.getPhone());
            // if (existingUsers != null) {
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            // .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Username
            // already exists"));
            // }

            personal createdUser = authenticationService.register(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new responseHelper<>("User registrated successfully", HttpStatus.OK,
                            createdUser, null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null,
                            e.getMessage()));
        }
    }

    @PostMapping("/login") // Login a user
    public ResponseEntity<responseHelper<Object>> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            System.out.println("Datos de la solicitud de autenticación: " + request.toString());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new responseHelper<>("Success login!", HttpStatus.OK, authenticationService.login(request),
                            null));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null,
                            "User not found: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null,
                            "Credentials are incorrect: " + e));
        }
    }

    @PutMapping("/changePassword") // Change password for user
    public ResponseEntity<responseHelper<Object>> changePassword(@RequestBody Map<String, String> requestBody) {
        try {
            String emailNew = requestBody.get("email");
            String passwordNew = requestBody.get("password");
            userService.changePassword(emailNew, passwordNew);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new responseHelper<>("Password changed successfully", HttpStatus.OK, true, null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, false, e.getMessage()));
        }
    }

}