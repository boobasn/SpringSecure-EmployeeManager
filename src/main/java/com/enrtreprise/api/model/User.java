package com.enrtreprise.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;

    @Column(nullable = false)
    private String role;

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_POINTEUR = "POINTEUR";
    public static final String ROLE_EMPLOYE = "EMPLOYE";
}
