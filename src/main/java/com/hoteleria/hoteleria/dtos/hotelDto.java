package com.hoteleria.hoteleria.dtos;

import java.util.Set;
import java.util.UUID;

import com.hoteleria.hoteleria.models.habitación;
import com.hoteleria.hoteleria.models.personal;

public class hotelDto {
    private UUID id;
    private String name;
    private String address;
    private int phone;
    private String email;
    private String description;
    private Set<personal> staff;
    private Set<habitación> rooms;

    public hotelDto() {
    }

    public hotelDto(UUID id, String name, String address, int phone, String email, String description,
            Set<personal> staff,
            Set<habitación> rooms) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.staff = staff;
        this.rooms = rooms;
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return this.phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<personal> getStaff() {
        return this.staff;
    }

    public void setStaff(Set<personal> staff) {
        this.staff = staff;
    }

    public Set<habitación> getRooms() {
        return this.rooms;
    }

    public void setRooms(Set<habitación> rooms) {
        this.rooms = rooms;
    }

}
