package com.hoteleria.hoteleria.dtos;

import java.util.UUID;

/* Dto class promocion */
public class promocionDto {
    private UUID id;
    private habitacionDto habitacion;
    private servicioDto servicio;
    private String descripcion;
    private String tipo_servicio;

    public promocionDto() {
    }

    public promocionDto(UUID id, habitacionDto habitacion, servicioDto servicio, String descripcion,
            String tipo_servicio) {
        this.id = id;
        this.habitacion = habitacion;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.tipo_servicio = tipo_servicio;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public habitacionDto getHabitacion() {
        return this.habitacion;
    }

    public void setHabitacion(habitacionDto habitacion) {
        this.habitacion = habitacion;
    }

    public servicioDto getServicio() {
        return this.servicio;
    }

    public void setServicio(servicioDto servicio) {
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

    public static class habitacionDto {
        private UUID id;
        private String tipo;
        private Double precio;

        public habitacionDto() {
        }

        public habitacionDto(UUID id, String tipo, Double precio) {
            this.id = id;
            this.tipo = tipo;
            this.precio = precio;
        }

        public UUID getId() {
            return this.id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getTipo() {
            return this.tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public Double getPrecio() {
            return this.precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }
    }

    public static class servicioDto {
        private UUID id;
        private String nombre;

        public servicioDto() {
        }

        public servicioDto(UUID id, String nombre) {
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
