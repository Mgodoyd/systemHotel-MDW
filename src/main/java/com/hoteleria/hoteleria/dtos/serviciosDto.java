package com.hoteleria.hoteleria.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

/* Dto class servicios */
public class serviciosDto {

    private UUID id;
    private reservacionDTO reservacion;
    private String nombre;
    private String descripcion;
    private Double precio;

    public serviciosDto() {
    }

    public serviciosDto(UUID id, reservacionDTO reservacion, String nombre, String descripcion, Double precio) {
        this.id = id;
        this.reservacion = reservacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public reservacionDTO getReservacion() {
        return this.reservacion;
    }

    public void setReservacion(reservacionDTO reservacion) {
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

    public static class reservacionDTO {
        private UUID id;
        private LocalDateTime fecha_reservacion;

        public reservacionDTO() {
        }

        public reservacionDTO(UUID id, LocalDateTime fecha_reservacion) {
            this.id = id;
            this.fecha_reservacion = fecha_reservacion;
        }

        public UUID getId() {
            return this.id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public LocalDateTime getFecha_reservacion() {
            return this.fecha_reservacion;
        }

        public void setFecha_reservacion(LocalDateTime fecha_reservacion) {
            this.fecha_reservacion = fecha_reservacion;
        }

    }
}
