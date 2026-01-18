package com.textonly.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer amount; // Can be negative for spent, positive for gained

    @Column(nullable = false)
    private String type; // "PURCHASE", "GIFT_SENT", "GIFT_RECEIVED", "BUY", "SELL"

    @Column(nullable = false)
    private Long timestamp = System.currentTimeMillis();
}
