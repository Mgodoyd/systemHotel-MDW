package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

/* Entity servicio */
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
    @JsonIgnore
    private Set<servicioHabitacion> serviciosHabitacion;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<promocion> promociones;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<parqueo> parqueos;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<usoInstalacion> usoInstalaciones;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public servicio() {
    }

    public servicio(Builder builder) {
        this.id = builder.id;
        this.reservacion = builder.reservacion;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.precio = builder.precio;
        this.serviciosHabitacion = builder.serviciosHabitacion;
        this.promociones = builder.promociones;
        this.parqueos = builder.parqueos;
        this.usoInstalaciones = builder.usoInstalaciones;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static class Builder {
        private UUID id;
        private reservacion reservacion;
        private String nombre;
        private String descripcion;
        private Double precio;
        private Set<servicioHabitacion> serviciosHabitacion;
        private Set<promocion> promociones;
        private Set<parqueo> parqueos;
        private Set<usoInstalacion> usoInstalaciones;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder reservacion(reservacion reservacion) {
            this.reservacion = reservacion;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder precio(Double precio) {
            this.precio = precio;
            return this;
        }

        public Builder serviciosHabitacion(Set<servicioHabitacion> serviciosHabitacion) {
            this.serviciosHabitacion = serviciosHabitacion;
            return this;
        }

        public Builder promociones(Set<promocion> promociones) {
            this.promociones = promociones;
            return this;
        }

        public Builder parqueos(Set<parqueo> parqueos) {
            this.parqueos = parqueos;
            return this;
        }

        public Builder usoInstalaciones(Set<usoInstalacion> usoInstalaciones) {
            this.usoInstalaciones = usoInstalaciones;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public servicio build() {
            return new servicio(this);
        }
    }

    public UUID getId() {
        return this.id;
    }

    public reservacion getReservacion() {
        return this.reservacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public Set<servicioHabitacion> getServiciosHabitacion() {
        return this.serviciosHabitacion;
    }

    public Set<promocion> getPromociones() {
        return this.promociones;
    }

    public Set<parqueo> getParqueos() {
        return this.parqueos;
    }

    public Set<usoInstalacion> getUsoInstalaciones() {
        return this.usoInstalaciones;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}