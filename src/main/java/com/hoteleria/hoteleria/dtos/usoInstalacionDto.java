package com.hoteleria.hoteleria.dtos;

import java.util.UUID;

/* Dto class usoInstalacion */
public class usoInstalacionDto {
    private UUID id;
    private servicioDTO servicio;
    private String descripcion;
    private Float monto;

    public usoInstalacionDto() {
    }

    public usoInstalacionDto(UUID id, servicioDTO servicio, String descripcion, Float monto) {
        this.id = id;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.monto = monto;
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

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getMonto() {
        return this.monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public static class servicioDTO {
        private UUID id;
        private String nombre;

        public servicioDTO() {
        }

        public servicioDTO(UUID id, String nombre) {
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
