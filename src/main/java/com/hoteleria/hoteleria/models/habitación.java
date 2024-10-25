package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.hoteleria.hoteleria.models.*;

/* Entity habitación */
@Entity
@Table(name = "habitacion")
public class habitación {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_hotel", nullable = false)
    @JsonIgnore
    private hotel hotel;

    @Column(length = 10, nullable = false)
    @Pattern(regexp = "^[0-9]+$", message = "El número debe contener solo dígitos.")
    private String numero;

    @Column(length = 50)
    private String tipo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 255, nullable = false)
    @Pattern(regexp = ".*\\.(jpg|jpeg|png|gif)$", message = "La imagen debe ser un archivo de imagen válido (jpg, jpeg, png, gif).")
    private String imagen;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double precio;

    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<reservacion> reservaciones = new HashSet<>();

    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<promocion> promociones = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public habitación() {
    }

    public habitación(Builder builder) {
        this.id = builder.id;
        this.hotel = builder.hotel;
        this.numero = builder.numero;
        this.tipo = builder.tipo;
        this.descripcion = builder.descripcion;
        this.imagen = builder.imagen;
        this.precio = builder.precio;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static class Builder {
        private UUID id;
        private hotel hotel;
        private String numero;
        private String tipo;
        private String descripcion;
        private String imagen;
        private Double precio;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder hotel(hotel hotel) {
            this.hotel = hotel;
            return this;
        }

        public Builder numero(String numero) {
            this.numero = numero;
            return this;
        }

        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder imagen(String imagen) {
            this.imagen = imagen;
            return this;
        }

        public Builder precio(Double precio) {
            this.precio = precio;
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

        public habitación build() {
            return new habitación(this);
        }

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

    public String getImagen() {
        return this.imagen;
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

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}