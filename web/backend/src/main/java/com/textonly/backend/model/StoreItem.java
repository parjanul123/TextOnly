package com.textonly.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // "EMOTICON", "FRAME"

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String resourceName; // E.g., "frame_neon_blue", "emote_smile"
}
