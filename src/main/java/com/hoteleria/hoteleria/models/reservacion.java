package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/* Entity reservacion */
@Entity
@Table(name = "reservacion")
public class reservacion {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_habitacion", nullable = false)
    private habitación habitacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_entrada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_salida;

    @Column(length = 50)
    private String estado;

    @CreationTimestamp
    private LocalDateTime fecha_reservacion;

    @OneToMany(mappedBy = "reservacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<servicio> servicios = new HashSet<>();

    @OneToMany(mappedBy = "reservacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<factura> facturas = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public reservacion() {
    }

    public reservacion(UUID id) {
        this.id = id;
    }

    public reservacion(UUID id, cliente cliente, habitación habitacion, Date fecha_entrada, Date fecha_salida,
            String estado, LocalDateTime fecha_reservacion, Set<servicio> servicios, Set<factura> facturas,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
        this.estado = estado;
        this.facturas = facturas;
        this.servicios = servicios;
        this.fecha_reservacion = fecha_reservacion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public habitación getHabitacion() {
        return this.habitacion;
    }

    public void setHabitacion(habitación habitacion) {
        this.habitacion = habitacion;
    }

    public Date getFecha_entrada() {
        return this.fecha_entrada;
    }

    public void setFecha_entrada(Date fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    public Date getFecha_salida() {
        return this.fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha_reservacion() {
        return this.fecha_reservacion;
    }

    public void setFecha_reservacion(LocalDateTime fecha_reservacion) {
        this.fecha_reservacion = fecha_reservacion;
    }

    public Set<servicio> getServicios() {
        return this.servicios;
    }

    public Set<factura> getFacturas() {
        return this.facturas;
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