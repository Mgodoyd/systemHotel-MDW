package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "factura")
public class factura {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_reservacion", nullable = false)
    @JsonIgnore
    private reservacion reservacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_emision;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double monto_total;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public factura() {
    }

    public factura(Builder builder) {
        this.id = builder.id;
        this.reservacion = builder.reservacion;
        this.fecha_emision = builder.fecha_emision;
        this.descripcion = builder.descripcion;
        this.monto_total = builder.monto_total;
    }

    public static class Builder {
        private UUID id;
        private reservacion reservacion;
        private Date fecha_emision;
        private String descripcion;
        private Double monto_total;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder reservacion(reservacion reservacion) {
            this.reservacion = reservacion;
            return this;
        }

        public Builder fecha_emision(Date fecha_emision) {
            this.fecha_emision = fecha_emision;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder monto_total(Double monto_total) {
            this.monto_total = monto_total;
            return this;
        }

        public factura build() {
            return new factura(this);
        }
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public reservacion getReservacion() {
        return this.reservacion;
    }

    public void setReservacion(reservacion reservacion) {
        this.reservacion = reservacion;
    }

    public Date getFecha_emision() {
        return this.fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto_total() {
        return this.monto_total;
    }

    public void setMonto_total(Double monto_total) {
        this.monto_total = monto_total;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}