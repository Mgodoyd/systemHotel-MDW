package com.hoteleria.hoteleria.dtos;

import java.util.Set;
import java.util.UUID;

import com.hoteleria.hoteleria.models.promocion;
import com.hoteleria.hoteleria.models.reservacion;

/* Dto class habitacion */
public class habitacionDto {
    private UUID id;
    private hotelDTOW hotel;
    private String numero;
    private String tipo;
    private String descripcion;
    private String imagen;
    private Double precio;
    private Set<reservacion> reservaciones;
    private Set<promocion> promociones;

    public habitacionDto() {
    }

    public habitacionDto(UUID id, hotelDTOW hotel, String numero, String tipo, String descripcion, String imagen,
            Double precio,
            Set<reservacion> reservaciones, Set<promocion> promociones) {
        this.id = id;
        this.hotel = hotel;
        this.numero = numero;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precio = precio;
        this.reservaciones = reservaciones;
        this.promociones = promociones;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public hotelDTOW getHotel() {
        return this.hotel;
    }

    public void setHotel(hotelDTOW hotel) {
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

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public static class hotelDTOW {
        private UUID id;

        public hotelDTOW() {
        }

        public hotelDTOW(UUID id) {
            this.id = id;
        }

        public UUID getId() {
            return this.id;
        }

        public void setId(UUID id) {
            this.id = id;
        }
    }

}
