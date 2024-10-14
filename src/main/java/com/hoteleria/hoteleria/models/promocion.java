package com.hoteleria.hoteleria.models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "promociones")
public class promocion {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private habitación habitacion;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private servicio servicio;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 50)
    private String tipo_servicio;

    @OneToMany(mappedBy = "promocion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<descuentos> descuentos;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public promocion() {
    }

    public promocion(UUID id, habitación habitacion, servicio servicio, String descripcion, String tipo_servicio,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.habitacion = habitacion;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.tipo_servicio = tipo_servicio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public habitación getHabitacion() {
        return this.habitacion;
    }

    public void setHabitacion(habitación habitacion) {
        this.habitacion = habitacion;
    }

    public servicio getServicio() {
        return this.servicio;
    }

    public void setServicio(servicio servicio) {
        this.servicio = servicio;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo_servicio() {
        return this.tipo_servicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    public Set<descuentos> getDescuentos() {
        return this.descuentos;
    }

    public void setDescuentos(Set<descuentos> descuentos) {
        this.descuentos = descuentos;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

}