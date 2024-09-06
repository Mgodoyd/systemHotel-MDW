package com.hoteleria.hoteleria.services.auth;

public class AuthenticationResponse {

    private String jwt;
    private Long expiration;

    public AuthenticationResponse(Long expiration, String jwt) {
        this.jwt = jwt;
        this.expiration = expiration;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}
