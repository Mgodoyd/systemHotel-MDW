package com.hoteleria.hoteleria.dtos;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.hoteleria.hoteleria.models.*;

/* Dto class reservacion */
public class reservacionDto {
    private UUID id;
    private personalDTO cliente;
    private habitacionDTO habitacion;
    private LocalDateTime fecha_reservacion;
    private Date fecha_entrada;
    private Date fecha_salida;
    private String estado;
    private Set<servicio> servicios;
    private Set<factura> facturas;

    public reservacionDto() {
    }

    public reservacionDto(UUID id, personalDTO cliente, habitacionDTO habitacion, LocalDateTime fecha_reservacion,
            Date fecha_entrada, Date fecha_salida,
            String estado, Set<servicio> servicios, Set<factura> facturas) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fecha_reservacion = fecha_reservacion;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
        this.estado = estado;
        this.servicios = servicios;
        this.facturas = facturas;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public personalDTO getCliente() {
        return this.cliente;
    }

    public void setCliente(personalDTO cliente) {
        this.cliente = cliente;
    }

    public habitacionDTO getHabitacion() {
        return this.habitacion;
    }

    public void setHabitacion(habitacionDTO habitacion) {
        this.habitacion = habitacion;
    }

    public LocalDateTime getFecha_reservacion() {
        return this.fecha_reservacion;
    }

    public void setFecha_reservacion(LocalDateTime fecha_reservacion) {
        this.fecha_reservacion = fecha_reservacion;
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

    public Set<servicio> getServicios() {
        return this.servicios;
    }

    public void setServicios(Set<servicio> servicios) {
        this.servicios = servicios;
    }

    public Set<factura> getFacturas() {
        return this.facturas;
    }

    public void setFacturas(Set<factura> facturas) {
        this.facturas = facturas;
    }

    public static class personalDTO {
        private UUID id;
        private String nombre;
        private String nit;
        private String email;

        public personalDTO() {
        }

        public personalDTO(UUID id, String nombre, String nit, String email) {
            this.id = id;
            this.nombre = nombre;
            this.nit = nit;
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

        public String getNit() {
            return this.nit;
        }

        public void setNit(String nit) {
            this.nit = nit;
        }

        public String getEmail() {
            return this.email;
        }
    }

    public static class habitacionDTO {
        private UUID id;
        private String numero;
        private String tipo;

        public habitacionDTO() {
        }

        public habitacionDTO(UUID id, String numero, String tipo) {
            this.id = id;
            this.numero = numero;
            this.tipo = tipo;
        }

        public UUID getId() {
            return this.id;
        }

        public void setId(UUID id) {
            this.id = id;
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
    }

}
