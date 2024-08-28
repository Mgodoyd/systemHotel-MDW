package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "factura")
public class factura {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_reservacion", nullable = false)
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

    public factura(UUID id, reservacion reservacion, Date fecha_emision, String descripcion, Double monto_total,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.reservacion = reservacion;
        this.fecha_emision = fecha_emision;
        this.descripcion = descripcion;
        this.monto_total = monto_total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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