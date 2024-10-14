package com.hoteleria.hoteleria.dtos;

import java.util.UUID;

/* Dto class servicioHabitacion */
public class servicioHabitacionDto {
    private UUID id;
    private servicioDTO servicio;
    private Integer cantidad;
    private Double precio_total;

    public servicioHabitacionDto() {
    }

    public servicioHabitacionDto(UUID id, servicioDTO servicio, Integer cantidad, Double precio_total) {
        this.id = id;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.precio_total = precio_total;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public servicioDTO getServicio() {
        return this.servicio;
    }

    public void setServicio(servicioDTO servicio) {
        this.servicio = servicio;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio_total() {
        return this.precio_total;
    }

    public void setPrecio_total(Double precio_total) {
        this.precio_total = precio_total;
    }

    public static class servicioDTO {
        private UUID id;
        private String nombre;

        public servicioDTO() {
        }

        public servicioDTO(UUID id, String nombre, Double precio) {
            this.id = id;
            this.nombre = nombre;
        }

        public UUID getId() {
            return this.id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getNombre() {
            return this.nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

    }

}
