package com.hoteleria.hoteleria.dtos;

import java.util.UUID;

import com.hoteleria.hoteleria.models.hotel;
import com.hoteleria.hoteleria.models.puesto;
import com.hoteleria.hoteleria.models.role;

public class staffDto {

    private UUID id;
    private UUID rolId;
    private String rolNombre;
    private UUID hotelId;
    private String hotelNombre;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String address;
    private role role;

    // Constructor vacío
    public staffDto() {
    }

    // Constructor con todos los atributos
    public staffDto(UUID id, puesto rol, hotel hotel, String name, String phone, String email, String password,
            String address, role role) {
        this.id = id;
        this.rolId = rol != null ? rol.getId() : null;
        this.rolNombre = rol != null ? rol.getName() : null;
        this.hotelId = hotel != null ? hotel.getId() : null;
        this.hotelNombre = hotel != null ? hotel.getName() : null;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    // Getters y Setters

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRolId() {
        return this.rolId;
    }

    public void setRolId(UUID rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return this.rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public UUID getHotelId() {
        return this.hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelNombre() {
        return this.hotelNombre;
    }

    public void setHotelNombre(String hotelNombre) {
        this.hotelNombre = hotelNombre;
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
}
