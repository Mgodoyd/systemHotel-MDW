package com.hoteleria.hoteleria.models;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing different roles within the hotel management system.
 * Each role is associated with a specific set of permissions.
 */
public enum role {

    /* permission assignment by role */
    CLIENT(Arrays.asList(Permission.READ_ONE_USER, Permission.UPDATE_ONE_USER, Permission.DELETE_ONE_HABITACION,
            Permission.DELETE_ONE_RESERVACION,
            Permission.READ_ONE_FACTURA, Permission.READ_ALL_SERVICIOS, Permission.READ_ONE_HABITACION,
            Permission.UPDATE_ONE_HABITACION, Permission.READ_ONE_RESERVACION,
            Permission.READ_ONE_HUESPED, Permission.SAVE_ONE_HUESPED, Permission.SAVE_ONE_RESERVACION)),

    ADMIN(Arrays.asList(Permission.values())),

    EMPLEADO(Arrays.asList(Permission.READ_ONE_USER, Permission.UPDATE_ONE_USER, Permission.DELETE_ONE_HABITACION,
            Permission.DELETE_ONE_RESERVACION));

    private List<Permission> permissions;

    role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}