package com.hoteleria.hoteleria.dtos;

import java.util.Date;
import java.util.UUID;

/* Dto class factura */
public class facturaDto {
    private UUID id;
    private reservacionDTO reservacion;
    private Date fecha_emision;
    private String descripcion;
    private Double monto_total;

    public facturaDto() {
    }

    public facturaDto(UUID id, reservacionDTO reservacion, Date fecha_emision, String descripcion, Double monto_total) {
        this.id = id;
        this.reservacion = reservacion;
        this.fecha_emision = fecha_emision;
        this.descripcion = descripcion;
        this.monto_total = monto_total;
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

    public Date getFecha_emision() {
        return this.fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto_total() {
        return this.monto_total;
    }

    public void setMonto_total(Double monto_total) {
        this.monto_total = monto_total;
    }

    public static class reservacionDTO {
        private UUID id;
        private personalDTO cliente;
        private habitacionDTO habitacion;

        public reservacionDTO() {
        }

        public reservacionDTO(UUID id, personalDTO cliente, habitacionDTO habitacion) {
            this.id = id;
            this.cliente = cliente;
            this.habitacion = habitacion;
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

        public static class personalDTO {
            private UUID id;
            private String nombre;
            private String nit;

            public personalDTO() {
            }

            public personalDTO(UUID id, String nombre, String nit) {
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
}
