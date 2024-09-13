package com.hoteleria.hoteleria.models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "personal")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class personal implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_puesto", nullable = false)
    // @JsonBackReference
    private puesto rol;

    @ManyToOne
    @JoinColumn(name = "id_hotel", nullable = false)
    // @JsonBackReference
    private hotel hotel;

    @NotBlank(message = "Name is required")
    @Column(length = 100, nullable = false)
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

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public personal() {
    }

    public personal(UUID id, puesto puesto, hotel hotel, String name, String password, String phone, String email,
            String address, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rol = puesto;
        this.hotel = hotel;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public puesto getRol() {
        return this.rol;
    }

    public void setRol(puesto rol) {
        this.rol = rol;
    }

    public hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(hotel hotel) {
        this.hotel = hotel;
    }

    public String getName() {
        return this.name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public role getRole() {
        return this.role;
    }

    public void setRole(role role) {
        this.role = role;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

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
        List<GrantedAuthority> authorities = role.getPermissions().stream().map(
                permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name())).collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

}