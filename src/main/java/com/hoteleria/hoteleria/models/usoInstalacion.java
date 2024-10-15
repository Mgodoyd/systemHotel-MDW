package com.hoteleria.hoteleria.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

/* Entity usoInstalaciones */
@Entity
@Table(name = "usoInstalaciones")
public class usoInstalacion {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private servicio servicio;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Float monto;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public usoInstalacion() {
    }

    public usoInstalacion(Builder builder) {
        this.id = builder.id;
        this.servicio = builder.servicio;
        this.descripcion = builder.descripcion;
        this.monto = builder.monto;
    }

    public static class Builder {
        private UUID id;
        private servicio servicio;
        private String descripcion;
        private Float monto;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder servicio(servicio servicio) {
            this.servicio = servicio;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder monto(Float monto) {
            this.monto = monto;
            return this;
        }

        public usoInstalacion build() {
            return new usoInstalacion(this);
        }
    }

    public UUID getId() {
        return this.id;
    }

    public servicio getServicio() {
        return this.servicio;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Float getMonto() {
        return this.monto;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    // public usoInstalacion(UUID id, servicio servicio, String descripcion, Float
    // monto, LocalDateTime createdAt,
    // LocalDateTime updatedAt) {
    // this.id = id;
    // this.servicio = servicio;
    // this.descripcion = descripcion;
    // this.monto = monto;
    // this.createdAt = createdAt;
    // this.updatedAt = updatedAt;
    // }

    // public UUID getId() {
    // return this.id;
    // }

    // public void setId(UUID id) {
    // this.id = id;
    // }

    // public servicio getServicio() {
    // return this.servicio;
    // }

    // public void setServicio(servicio servicio) {
    // this.servicio = servicio;
    // }

    // public String getDescripcion() {
    // return this.descripcion;
    // }

    // public void setDescripcion(String descripcion) {
    // this.descripcion = descripcion;
    // }

    // public Float getMonto() {
    // return this.monto;
    // }

    // public void setMonto(Float monto) {
    // this.monto = monto;
    // }

    // public LocalDateTime getCreatedAt() {
    // return this.createdAt;
    // }

    // public LocalDateTime getUpdatedAt() {
    // return this.updatedAt;
    // }

}