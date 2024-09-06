package com.hoteleria.hoteleria.services.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoteleria.hoteleria.helpers.responseHelper;
import com.hoteleria.hoteleria.interfaces.personalInterface;
import com.hoteleria.hoteleria.models.personal;

import java.io.IOException;
import java.util.Optional;

/* jwt token filtering and validation */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private personalInterface userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1];
        System.out.println(jwt);

        try {
            if (!jwtService.isValidToken(jwt)) {
                throw new Exception("Invalid JWT Token");
            }

            String username = jwtService.extractUsername(jwt);
            Optional<personal> userjwt = userRepository.findByEmail(username);
            if (userjwt.isEmpty()) {
                throw new Exception("User not found");
            }

            UsernamePasswordAuthenticationToken authjwt = new UsernamePasswordAuthenticationToken(
                    userjwt.orElseThrow(), null,
                    userjwt.orElseThrow().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authjwt);

        } catch (Exception e) {
            // Handle invalid token or authentication errors
            responseHelper<Object> errorResponse = new responseHelper<>("Error ",
                    HttpStatus.UNAUTHORIZED, null, e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), errorResponse);
            return;
        }

        filterChain.doFilter(request, response);
    }
}