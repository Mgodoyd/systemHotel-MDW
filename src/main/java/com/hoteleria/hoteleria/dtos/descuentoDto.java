package com.hoteleria.hoteleria.dtos;

import java.util.Date;
import java.util.UUID;

public class descuentoDto {
    private UUID id;
    private promocionDTO promocion;
    private String codigo;
    private String tipo_de_descuento;
    private Double valor_descuento;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String estado;

    public descuentoDto() {
    }

    public descuentoDto(UUID id, promocionDTO promocion, String codigo, String tipo_de_descuento,
            Double valor_descuento,
            Date fecha_inicio, Date fecha_fin, String estado) {
        this.id = id;
        this.promocion = promocion;
        this.codigo = codigo;
        this.tipo_de_descuento = tipo_de_descuento;
        this.valor_descuento = valor_descuento;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estado = estado;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public promocionDTO getPromocion() {
        return this.promocion;
    }

    public void setPromocion(promocionDTO promocion) {
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

    public static class promocionDTO {
        private UUID id;
        private String descripcion;

        public promocionDTO() {
        }

        public promocionDTO(UUID id, String descripcion) {
            this.id = id;
            this.descripcion = descripcion;
        }

        public UUID getId() {
            return this.id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getDescripcion() {
            return this.descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

    }

}
