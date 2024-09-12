package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "hotel")
public class hotel {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Name is required")
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank(message = "Address is required")
    @Column(length = 50, nullable = false)
    private String address;

    @NotNull(message = "Phone is required")
    @Column(length = 8, nullable = false)
    @Min(value = 10000000, message = "The phone number must have at least 8 digits.")
    @PositiveOrZero(message = "The phone number must be positive or zero.")
    private int phone;

    @Column(length = 25)
    @Pattern(message = "Email is not valid", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "hotel")
    private Set<personal> staff;

    @OneToMany(mappedBy = "hotel")
    private Set<habitación> rooms;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public hotel() {
    }

    public hotel(UUID id, String name, String address, int phone, String email, String description,
            Set<personal> staff,
            Set<habitación> rooms, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.staff = staff;
        this.rooms = rooms;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

}