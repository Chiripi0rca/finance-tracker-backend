package com.finance_tracker.finance_tracker_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Data
public class RefreshTokenEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column (nullable = false, unique = true)
     private String token;

     @Column(nullable = false)
     private LocalDateTime expiryDate;

     @OneToOne
     @JoinColumn (name = "user_id")
     private UserEntity user;

}
