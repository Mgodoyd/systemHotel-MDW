package com.hoteleria.hoteleria.models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "personal")
public class personal {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_puesto", nullable = false)
    private puesto puesto;

    @ManyToOne
    @JoinColumn(name = "id_hotel", nullable = false)
    private hotel hotel;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String direccion;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public personal() {
    }

    public personal(UUID id, puesto puesto, hotel hotel, String nombre, String telefono, String email,
            String direccion, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.puesto = puesto;
        this.hotel = hotel;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public puesto getPuesto() {
        return this.puesto;
    }

    public void setPuesto(puesto puesto) {
        this.puesto = puesto;
    }

    public hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(hotel hotel) {
        this.hotel = hotel;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

}