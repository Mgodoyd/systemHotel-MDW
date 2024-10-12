package com.hoteleria.hoteleria.models;

/**
 * The Permission enum defines a set of permissions for various operations
 * within the hotel management system. Each permission corresponds to a specific
 * action that can be performed on different entities such as Puestos, Hotels,
 * Users, Habitaciones, Servicios, Reservaciones, Facturas, and Huespedes.
 * 
 * Permissions include:
 * - READ: Permission to read or view the entity.
 * - READ_ONE: Permission to read or view a single instance of the entity.
 * - SAVE_ONE: Permission to create or save a new instance of the entity.
 * - UPDATE_ONE: Permission to update an existing instance of the entity.
 * - DELETE_ONE: Permission to delete an existing instance of the entity.
 * 
 * Entities covered by these permissions:
 * - Puestos
 * - Hotels
 * - Users
 * - Habitaciones
 * - Servicios
 * - Reservaciones
 * - Facturas
 * - Huespedes
 */
public enum Permission {

    READ_PUESTOS,

    READ_ONE_PUESTO,

    SAVE_ONE_PUESTO,

    UPDATE_ONE_PUESTO,

    DELETE_ONE_PUESTO,

    READ_HOTEL,

    READ_ONE_HOTEL,

    SAVE_ONE_HOTEL,

    UPDATE_ONE_HOTEL,

    DELETE_ONE_HOTEL,

    READ_ALL_USERS,

    READ_ONE_USER,

    SAVE_ONE_USER,

    UPDATE_ONE_USER,

    DELETE_ONE_USER,

    READ_ALL_HABITACIONES,

    READ_ONE_HABITACION,

    SAVE_ONE_HABITACION,

    UPDATE_ONE_HABITACION,

    DELETE_ONE_HABITACION,

    READ_ALL_SERVICIOS,

    READ_ONE_SERVICIO,

    SAVE_ONE_SERVICIO,

    UPDATE_ONE_SERVICIO,

    DELETE_ONE_SERVICIO,

    READ_ALL_RESERVACIONES,

    READ_ONE_RESERVACION,

    SAVE_ONE_RESERVACION,

    UPDATE_ONE_RESERVACION,

    DELETE_ONE_RESERVACION,

    READ_ALL_FACTURAS,

    READ_ONE_FACTURA,

    SAVE_ONE_FACTURA,

    UPDATE_ONE_FACTURA,

    DELETE_ONE_FACTURA,

    READ_ALL_HUESPEDES,

    READ_ONE_HUESPED,

    SAVE_ONE_HUESPED,

    UPDATE_ONE_HUESPED,

    DELETE_ONE_HUESPED,
}