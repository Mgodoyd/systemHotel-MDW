package com.hoteleria.hoteleria.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.interfaces.clienteInterface;
import com.hoteleria.hoteleria.interfaces.personalInterface;
import com.hoteleria.hoteleria.models.cliente;
import com.hoteleria.hoteleria.models.personal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Authentication service
 */
@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private personalInterface userRepository;

    @Autowired
    private clienteInterface clienteRepository;

    @Autowired
    private JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an AuthenticationService with the specified PasswordEncoder.
     *
     * @param passwordEncoder the PasswordEncoder to be used for encoding passwords
     */
    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new personal user with the provided details.
     *
     * @param request the personal user details to register
     * @return the registered personal user
     */
    public personal register(personal request) {

        System.out.println(request);
        personal user = new personal.Builder(UUID.randomUUID())
                .rol(request.getRol())
                .hotel(request.getHotel())
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return userRepository.save(user);
    }

    /**
     * Authenticates a user based on the provided authentication request.
     * 
     * @param authenticationRequest the request containing the user's email and
     *                              password
     * @return an AuthenticationResponse containing the JWT token and its expiration
     *         time in minutes
     */
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmailString(), authenticationRequest.getPassword());
            authenticationManager.authenticate(authToken);

            Optional<personal> personalUser = userRepository.findByEmail(authenticationRequest.getEmailString());
            Optional<cliente> clienteUser = clienteRepository.findByEmail(authenticationRequest.getEmailString());

            if (personalUser.isPresent()) {
                String jwt = jwtService.generateToken(personalUser.get(), generateExtraClaims(personalUser.get()));
                Date expirationDate = jwtService.getExpirationDate(jwt);
                long expirationTimeMillis = expirationDate.getTime() - System.currentTimeMillis();
                long expirationTimeMinutes = expirationTimeMillis / 1000;
                return new AuthenticationResponse(expirationTimeMinutes, jwt);
            } else if (clienteUser.isPresent()) {
                String jwt = jwtService.generateToken(clienteUser.get(), generateExtraClaims(clienteUser.get()));
                Date expirationDate = jwtService.getExpirationDate(jwt);
                long expirationTimeMillis = expirationDate.getTime() - System.currentTimeMillis();
                long expirationTimeMinutes = expirationTimeMillis / 1000;
                return new AuthenticationResponse(expirationTimeMinutes, jwt);
            } else {
                throw new UsernameNotFoundException(
                        "User not found with email: " + authenticationRequest.getEmailString());
            }
        } catch (Exception e) {
            // Agregar registro de depuración
            System.err.println("Error during authentication: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Generates a map of extra claims for a given user.
     *
     * @param user the user for whom the extra claims are generated
     * @return a map containing the extra claims, including the user's name and role
     */
    private Map<String, Object> generateExtraClaims(personal user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("id", user.getId());
        return extraClaims;
    }

    /**
     * Generates a map of extra claims for a given user.
     *
     * @param user the user for whom the extra claims are generated
     * @return a map containing the extra claims, including the user's name and role
     */

    private Map<String, Object> generateExtraClaims(cliente user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getNombre());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("id", user.getId());
        return extraClaims;
    }
}