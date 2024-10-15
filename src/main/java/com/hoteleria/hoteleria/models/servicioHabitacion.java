package com.hoteleria.hoteleria.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

/* Entity servicioHabitacion */
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

    public servicioHabitacion(Builder builder) {
        this.id = builder.id;
        this.servicio = builder.servicio;
        this.cantidad = builder.cantidad;
        this.precio_total = builder.precio_total;
    }

    public static class Builder {
        private UUID id;
        private servicio servicio;
        private Integer cantidad;
        private Double precio_total;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder servicio(servicio servicio) {
            this.servicio = servicio;
            return this;
        }

        public Builder cantidad(Integer cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public Builder precio_total(Double precio_total) {
            this.precio_total = precio_total;
            return this;
        }

        public servicioHabitacion build() {
            return new servicioHabitacion(this);
        }
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

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}