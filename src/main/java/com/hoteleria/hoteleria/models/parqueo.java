package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "parqueo")
public class parqueo {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private servicio servicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_inicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_fin;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double monto;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public parqueo() {
    }

    public parqueo(Builder builder) {
        this.id = builder.id;
        this.servicio = builder.servicio;
        this.fecha_inicio = builder.fecha_inicio;
        this.fecha_fin = builder.fecha_fin;
        this.monto = builder.monto;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static class Builder {
        private UUID id;
        private servicio servicio;
        private Date fecha_inicio;
        private Date fecha_fin;
        private Double monto;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder servicio(servicio servicio) {
            this.servicio = servicio;
            return this;
        }

        public Builder fecha_inicio(Date fecha_inicio) {
            this.fecha_inicio = fecha_inicio;
            return this;
        }

        public Builder fecha_fin(Date fecha_fin) {
            this.fecha_fin = fecha_fin;
            return this;
        }

        public Builder monto(Double monto) {
            this.monto = monto;
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

        public parqueo build() {
            return new parqueo(this);
        }
    }

    public UUID getId() {
        return this.id;
    }

    public servicio getServicio() {
        return this.servicio;
    }

    public Date getFecha_inicio() {
        return this.fecha_inicio;
    }

    public Date getFecha_fin() {
        return this.fecha_fin;
    }

    public Double getMonto() {
        return this.monto;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    // public parqueo(UUID id, servicio servicio, Date fecha_inicio, Date fecha_fin,
    // Double monto, LocalDateTime createdAt,
    // LocalDateTime updatedAt) {
    // this.id = id;
    // this.servicio = servicio;
    // this.fecha_inicio = fecha_inicio;
    // this.fecha_fin = fecha_fin;
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

    // public Date getFecha_inicio() {
    // return this.fecha_inicio;
    // }

    // public void setFecha_inicio(Date fecha_inicio) {
    // this.fecha_inicio = fecha_inicio;
    // }

    // public Date getFecha_fin() {
    // return this.fecha_fin;
    // }

    // public void setFecha_fin(Date fecha_fin) {
    // this.fecha_fin = fecha_fin;
    // }

    // public Double getMonto() {
    // return this.monto;
    // }

    // public void setMonto(Double monto) {
    // this.monto = monto;
    // }

    // public LocalDateTime getCreatedAt() {
    // return this.createdAt;
    // }

    // public LocalDateTime getUpdatedAt() {
    // return this.updatedAt;
    // }

}