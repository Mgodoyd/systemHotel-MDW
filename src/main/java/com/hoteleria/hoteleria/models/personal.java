package com.hoteleria.hoteleria.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "personal")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class personal implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_puesto", nullable = false)
    private puesto rol;

    @ManyToOne
    @JoinColumn(name = "id_hotel", nullable = false)
    @JsonIgnore
    private hotel hotel;

    @NotBlank(message = "Name is required")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank(message = "Phone is required")
    @Column(length = 8, nullable = false)
    @Min(value = 10000000, message = "The phone number must have at least 8 digits.")
    @PositiveOrZero(message = "The phone number must be positive or zero.")
    private String phone;

    @NotBlank(message = "Email is required")
    @Pattern(message = "Email is not valid", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 50)
    private String nit;

    @NotBlank(message = "Password is required")
    @Column(length = 200)
    private String password;

    @NotBlank(message = "Address is required")
    @Column(length = 50)
    private String address;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private role role;

    @OneToMany(mappedBy = "personal")
    @JsonIgnore
    private Set<reservacion> reservaciones;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public personal() {
    }

    private personal(Builder builder) {
        this.id = builder.id;
        this.rol = builder.rol;
        this.hotel = builder.hotel;
        this.name = builder.name;
        this.phone = builder.phone;
        this.email = builder.email;
        this.password = builder.password;
        this.address = builder.address;
        this.reservaciones = builder.reservaciones;
        this.role = builder.role;
        this.nit = builder.nit;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static class Builder {
        private UUID id;
        private puesto rol;
        private hotel hotel;
        private String name;
        private String phone;
        private String email;
        private String nit;
        private Set<reservacion> reservaciones;
        private String password;
        private String address;
        private role role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder(UUID id) {
            this.id = id;
        }

        public Builder rol(puesto rol) {
            this.rol = rol;
            return this;
        }

        public Builder hotel(hotel hotel) {
            this.hotel = hotel;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder nit(String nit) {
            this.nit = nit;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder role(role role) {
            this.role = role;
            return this;
        }

        public Builder reservaciones(Set<reservacion> reservaciones) {
            this.reservaciones = reservaciones;
            return this;
        }

        public personal build() {
            return new personal(this);
        }
    }

    // Getters y Setters
    public UUID getId() {
        return this.id;
    }

    public puesto getRol() {
        return this.rol;
    }

    public hotel getHotel() {
        return this.hotel;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getNit() {
        return this.nit;
    }

    public String getEmail() {
        return this.email;
    }

    public Set<reservacion> getReservaciones() {
        return this.reservaciones;
    }

    public role getRole() {
        return this.role;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return this.address;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    // Métodos de UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = role.getPermissions().stream()
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
}