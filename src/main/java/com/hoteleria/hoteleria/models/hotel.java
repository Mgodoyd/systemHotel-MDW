package com.hoteleria.hoteleria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.*;

/* Entity hotel */
@Entity
@Table(name = "hotel")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "staff", "hotel" })
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

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonManagedReference
    @JsonIgnore
    private Set<personal> staff;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<habitación> rooms;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public hotel() {
    }

    private hotel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
        this.phone = builder.phone;
        this.email = builder.email;
        this.description = builder.description;
        this.staff = builder.staff;
        this.rooms = builder.rooms;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String address;
        private int phone;
        private String email;
        private String description;
        private Set<personal> staff;
        private Set<habitación> rooms;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phone(int phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder staff(Set<personal> staff) {
            this.staff = staff;
            return this;
        }

        public Builder rooms(Set<habitación> rooms) {
            this.rooms = rooms;
            return this;
        }

        public hotel build() {
            return new hotel(this);
        }
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDescription() {
        return this.description;
    }

    public Set<personal> getStaff() {
        return this.staff;
    }

    public Set<habitación> getRooms() {
        return this.rooms;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

}