package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "servicios")
public class servicio {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_reservacion", nullable = false)
    private reservacion reservacion;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double precio;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<servicioHabitacion> serviciosHabitacion;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<promocion> promociones;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<parqueo> parqueos;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<usoInstalacion> usoInstalaciones;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public servicio() {
    }

    public servicio(UUID id, reservacion reservacion, String nombre, String descripcion, Double precio,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.reservacion = reservacion;
        this.nombre = nombre;
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

    public reservacion getReservacion() {
        return this.reservacion;
    }

    public void setReservacion(reservacion reservacion) {
        this.reservacion = reservacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Set<servicioHabitacion> getServiciosHabitacion() {
        return this.serviciosHabitacion;
    }

    public void setServiciosHabitacion(Set<servicioHabitacion> serviciosHabitacion) {
        this.serviciosHabitacion = serviciosHabitacion;
    }

    public Set<promocion> getPromociones() {
        return this.promociones;
    }

    public void setPromociones(Set<promocion> promociones) {
        this.promociones = promociones;
    }

    public Set<parqueo> getParqueos() {
        return this.parqueos;
    }

    public void setParqueos(Set<parqueo> parqueos) {
        this.parqueos = parqueos;
    }

    public Set<usoInstalacion> getUsoInstalaciones() {
        return this.usoInstalaciones;
    }

    public void setUsoInstalaciones(Set<usoInstalacion> usoInstalaciones) {
        this.usoInstalaciones = usoInstalaciones;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

}