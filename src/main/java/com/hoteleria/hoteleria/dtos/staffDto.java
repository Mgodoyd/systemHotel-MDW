package com.hoteleria.hoteleria.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hoteleria.hoteleria.models.role;

/* Dto class personal */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class staffDto {

    private UUID id;
    private rolDTO rol;
    private hotelDTO hotel;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String address;
    private role role;

    public staffDto() {
    }

    public staffDto(UUID id, rolDTO rol, hotelDTO hotel, String name, String phone, String email, String password,
            String address, role role) {
        this.id = id;
        this.rol = rol;
        this.hotel = hotel;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public rolDTO getRol() {
        return this.rol;
    }

    public void setRol(rolDTO rol) {
        this.rol = rol;
    }

    public hotelDTO getHotel() {
        return this.hotel;
    }

    public void setHotel(hotelDTO hotel) {
        this.hotel = hotel;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public role getRole() {
        return this.role;
    }

    public void setRole(role role) {
        this.role = role;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class hotelDTO {
        private UUID id;
        private String name;

        public hotelDTO() {
        }

        public hotelDTO(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        public UUID getId() {
            return this.id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class rolDTO {
        private UUID id;
        private String name;

        public rolDTO() {
        }

        public rolDTO(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        public UUID getId() {
            return this.id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
