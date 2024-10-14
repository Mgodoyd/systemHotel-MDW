package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "clientes")
public class cliente {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 50)
    private String nit;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private Set<reservacion> reservaciones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private role role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public cliente() {
    }

    public cliente(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.nit = builder.nit;
        this.telefono = builder.telefono;
        this.email = builder.email;
        this.direccion = builder.direccion;
        this.role = builder.role;
        this.reservaciones = builder.reservaciones;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static class Builder {
        private UUID id;
        private String nombre;
        private String nit;
        private String telefono;
        private String email;
        private String direccion;
        private role role;
        private Set<reservacion> reservaciones;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder nit(String nit) {
            this.nit = nit;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder role(role role) {
            this.role = role;
            return this;
        }

        public Builder reservaciones(Set<reservacion> reservaciones) {
            this.reservaciones = reservaciones;
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

        public cliente build() {
            return new cliente(this);
        }
    }

    public UUID getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getNit() {
        return this.nit;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public role getRole() {
        return this.role;
    }

    public Set<reservacion> getReservaciones() {
        return this.reservaciones;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    // public cliente(UUID id, String nombre, String nit, String telefono, String
    // email, String direccion,
    // LocalDateTime createdAt, LocalDateTime updatedAt) {
    // this.id = id;
    // this.nombre = nombre;
    // this.nit = nit;
    // this.telefono = telefono;
    // this.email = email;
    // this.direccion = direccion;
    // this.createdAt = createdAt;
    // this.updatedAt = updatedAt;
    // }

    // public UUID getId() {
    // return this.id;
    // }

    // public void setId(UUID id) {
    // this.id = id;
    // }

    // public String getNombre() {
    // return this.nombre;
    // }

    // public void setNombre(String nombre) {
    // this.nombre = nombre;
    // }

    // public String getNit() {
    // return this.nit;
    // }

    // public void setNit(String nit) {
    // this.nit = nit;
    // }

    // public String getTelefono() {
    // return this.telefono;
    // }

    // public void setTelefono(String telefono) {
    // this.telefono = telefono;
    // }

    // public String getEmail() {
    // return this.email;
    // }

    // public void setEmail(String email) {
    // this.email = email;
    // }

    // public String getDireccion() {
    // return this.direccion;
    // }

    // public void setDireccion(String direccion) {
    // this.direccion = direccion;
    // }

    // public role getRole() {
    // return this.role;
    // }

    // public void setRole(role role) {
    // this.role = role;
    // }

    // public Set<reservacion> getReservaciones() {
    // return this.reservaciones;
    // }

    // public void setReservaciones(Set<reservacion> reservaciones) {
    // this.reservaciones = reservaciones;
    // }

    // public LocalDateTime getCreatedAt() {
    // return this.createdAt;
    // }

    // public void setCreatedAt(LocalDateTime createdAt) {
    // this.createdAt = createdAt;
    // }

    // public LocalDateTime getUpdatedAt() {
    // return this.updatedAt;
    // }

    // public void setUpdatedAt(LocalDateTime updatedAt) {
    // this.updatedAt = updatedAt;
    // }

}