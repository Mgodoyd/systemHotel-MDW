package com.hoteleria.hoteleria.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.interfaces.personalInterface;
import com.hoteleria.hoteleria.models.personal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private personalInterface userRepository;

    @Autowired
    private JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public personal register(personal request) {
        var user = new personal();
        user.setRol(request.getRol());
        user.setHotel(request.getHotel());
        user.setname(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmailString(), authenticationRequest.getPassword());
        authenticationManager.authenticate(authToken);
        Optional<personal> user = userRepository.findByEmail(authenticationRequest.getEmailString());
        String jwt = jwtService.generateToken(user.get(), generateExtraClaims(user.get()));
        Date expirationDate = jwtService.getExpirationDate(jwt);
        long expirationTimeMillis = expirationDate.getTime() - System.currentTimeMillis();
        long expirationTimeMinutes = expirationTimeMillis / 1000;
        return new AuthenticationResponse(expirationTimeMinutes, jwt);
    }

    private Map<String, Object> generateExtraClaims(personal user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        return extraClaims;
    }
}