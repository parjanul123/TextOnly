package com.textonly.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    // Profil
    private String displayName;
    private String profileImageUri;

    // Portofel virtual
    private Double walletBalance = 0.0;

    // Sistem OnlyCoins
    @Column(nullable = false)
    private Integer coinBalance = 0;

    // 🔧 Constructor personalizat fără ID (pentru înregistrare)
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.coinBalance = 0;
    }
}
