package com.hoteleria.hoteleria.dtos;

import java.util.Set;
import java.util.UUID;

import com.hoteleria.hoteleria.models.*;

/* Dto class cliente */
public class clienteDto {
    private UUID id;
    private String nombre;
    private String nit;
    private String telefono;
    private String email;
    private String password;
    private String direccion;
    private Set<reservacion> reservaciones;
    private role role;

    public clienteDto() {
    }

    public clienteDto(UUID id, String nombre, String nit, String telefono, String email, String password,
            String direccion,
            Set<reservacion> reservaciones, role role) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.reservaciones = reservaciones;
        this.role = role;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return this.nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<reservacion> getReservaciones() {
        return this.reservaciones;
    }

    public void setReservaciones(Set<reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }

    public role getRole() {
        return this.role;
    }

    public void setRole(role role) {
        this.role = role;
    }

}
