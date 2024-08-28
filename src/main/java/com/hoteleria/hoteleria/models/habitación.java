package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "habitacion")
public class habitación {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_hotel", nullable = false)
    private hotel hotel;

    @Column(length = 10, nullable = false)
    private String numero;

    @Column(length = 50)
    private String tipo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double precio;

    @OneToMany(mappedBy = "habitacion")
    private Set<reservacion> reservaciones;

    @OneToMany(mappedBy = "habitacion")
    private Set<promocion> promociones;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public habitación() {
    }

    public habitación(UUID id, hotel hotel, String numero, String tipo, String descripcion, Double precio,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.hotel = hotel;
        this.numero = numero;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(hotel hotel) {
        this.hotel = hotel;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<reservacion> getReservaciones() {
        return this.reservaciones;
    }

    public void setReservaciones(Set<reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }

    public Set<promocion> getPromociones() {
        return this.promociones;
    }

    public void setPromociones(Set<promocion> promociones) {
        this.promociones = promociones;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

}