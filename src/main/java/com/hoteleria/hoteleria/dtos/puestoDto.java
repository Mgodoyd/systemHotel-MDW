package com.hoteleria.hoteleria.dtos;

import java.util.UUID;

/**
 * The puestoDto class represents a Data Transfer Object (DTO) for a job
 * position.
 * It contains information about the job position's unique identifier and name.
 */
public class puestoDto {

    private UUID id;
    private String name;

    public puestoDto() {
    }

    public puestoDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
