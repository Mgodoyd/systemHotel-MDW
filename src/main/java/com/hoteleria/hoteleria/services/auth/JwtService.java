package com.hoteleria.hoteleria.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import com.hoteleria.hoteleria.models.cliente;
import com.hoteleria.hoteleria.models.personal;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/* jwt token generation */
@Service
public class JwtService {

    private long EXPIRATION_MINUTES = 30;

    private String SECRET_KEY = "dGhpcyBpcyBteSBzZWN1cmUga2V5IGFuZCB5b3UgY2Fubm90IGhhY2sgaXQ=";

    /**
     * Generates a JWT token for the given user with additional claims.
     *
     * @param user        the user for whom the token is being generated
     * @param extraClaims a map of additional claims to be included in the token
     * @return a JWT token as a String
     */
    public String generateToken(personal user, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(cliente user, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a cryptographic key for HMAC-SHA signature algorithm using a secret
     * key.
     *
     * @return a {@link Key} object that can be used for HMAC-SHA signature.
     */
    private Key generateKey() {
        byte[] secreateAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secreateAsBytes);
    }

    /**
     * Extracts the username from the given JWT (JSON Web Token).
     *
     * @param jwt the JSON Web Token from which the username is to be extracted
     * @return the username extracted from the JWT
     */
    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    /**
     * Extracts all claims from the provided JWT.
     *
     * @param jwt the JSON Web Token from which to extract claims
     * @return the claims extracted from the JWT
     */
    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(jwt).getBody();
    }

    /**
     * Validates the provided JWT token.
     *
     * This method parses the JWT token and checks its expiration date to determine
     * if it is still valid.
     * If the token is valid and not expired, it returns true. Otherwise, it returns
     * false.
     *
     * @param token the JWT token to be validated
     * @return true if the token is valid and not expired, false otherwise
     */
    public boolean isValidToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the expiration date from the given JWT token.
     *
     * @param token the JWT token from which to extract the expiration date
     * @return the expiration date of the token
     */
    public Date getExpirationDate(String token) {
        @SuppressWarnings("deprecation")
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

}