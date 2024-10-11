package com.hoteleria.hoteleria.dtos;

import java.util.Date;
import java.util.UUID;

import com.hoteleria.hoteleria.models.servicio;

public class parqueoDto {
    private UUID id;
    private servicioDTO servicio;
    private Date fecha_inicio;
    private Date fecha_fin;
    private Double monto;

    public parqueoDto() {
    }

    public parqueoDto(UUID id, servicioDTO servicio, Date fecha_inicio, Date fecha_fin, Double monto) {
        this.id = id;
        this.servicio = servicio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
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

    public Double getMonto() {
        return this.monto;
    }

    public void setMonto(Double monto) {
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
