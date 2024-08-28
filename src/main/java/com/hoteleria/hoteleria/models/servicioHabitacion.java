package com.hoteleria.hoteleria.models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "serviciosHabitacion")
public class servicioHabitacion {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private servicio servicio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double precio_total;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public servicioHabitacion() {
    }

    public servicioHabitacion(UUID id, servicio servicio, Integer cantidad, Double precio_total,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.precio_total = precio_total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public servicio getServicio() {
        return this.servicio;
    }

    public void setServicio(servicio servicio) {
        this.servicio = servicio;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio_total() {
        return this.precio_total;
    }

    public void setPrecio_total(Double precio_total) {
        this.precio_total = precio_total;
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

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}