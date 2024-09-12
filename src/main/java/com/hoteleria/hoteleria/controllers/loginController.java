package com.hoteleria.hoteleria.controllers;

import java.lang.foreign.Linker.Option;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hoteleria.hoteleria.dtos.staffDto;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.models.personal;
import com.hoteleria.hoteleria.services.personalService;
import com.hoteleria.hoteleria.services.auth.AuthenticationRequest;
import com.hoteleria.hoteleria.services.auth.AuthenticationService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = { "*", "https://localhost/", "http://localhost:8100" }, methods = { RequestMethod.POST,
        RequestMethod.PUT }, allowedHeaders = { "Authorization", "Content-Type" })
@RequestMapping("/api/v1")
public class loginController {

    private final AuthenticationService authenticationService;

    @Autowired
    private personalService userService;

    public loginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register") // Register a new user
    public ResponseEntity<responseHelper<Object>> register(@RequestBody personal user) {
        try {

            staffDto existingEmail = userService.findByEmail(user.getEmail()).orElse(null);
            if (existingEmail == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Email already exists"));
            }

            staffDto existingUsers = userService.findByTelefono(user.getPhone());
            if (existingUsers != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Username already exists"));
            }

            personal createdUser = authenticationService.register(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new responseHelper<>("User registrated successfully", HttpStatus.OK,
                            createdUser, null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new responseHelper<>("Error", HttpStatus.INTERNAL_SERVER_ERROR, null, e));
        }
    }

    @PostMapping("/login") // Login a user
    public ResponseEntity<responseHelper<Object>> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new responseHelper<>("Success login!", HttpStatus.OK, authenticationService.login(request),
                            null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, "Credentials are incorrect" + e));
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