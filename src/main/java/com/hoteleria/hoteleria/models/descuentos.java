package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "descuentos")
public class descuentos {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_promocion", nullable = false)
    private promocion promocion;

    @Column(length = 50)
    private String codigo;

    @Column(length = 50)
    private String tipo_de_descuento;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double valor_descuento;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_inicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_fin;

    @Column(length = 50)
    private String estado;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public descuentos() {
    }

    public descuentos(UUID id, promocion promocion, String codigo, String tipo_de_descuento, Double valor_descuento,
            Date fecha_inicio, Date fecha_fin, String estado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.promocion = promocion;
        this.codigo = codigo;
        this.tipo_de_descuento = tipo_de_descuento;
        this.valor_descuento = valor_descuento;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public promocion getPromocion() {
        return this.promocion;
    }

    public void setPromocion(promocion promocion) {
        this.promocion = promocion;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo_de_descuento() {
        return this.tipo_de_descuento;
    }

    public void setTipo_de_descuento(String tipo_de_descuento) {
        this.tipo_de_descuento = tipo_de_descuento;
    }

    public Double getValor_descuento() {
        return this.valor_descuento;
    }

    public void setValor_descuento(Double valor_descuento) {
        this.valor_descuento = valor_descuento;
    }

    public Date getFecha_inicio() {
        return this.fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return this.fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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