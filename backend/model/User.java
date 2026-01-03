package com.skillswap.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table (name = "users")

public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    private Double balance = 0.0;

}
