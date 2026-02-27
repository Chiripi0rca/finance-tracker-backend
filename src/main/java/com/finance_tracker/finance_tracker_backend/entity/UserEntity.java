package com.finance_tracker.finance_tracker_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column (unique = true, nullable = false)
    private String email;

    @Column (nullable = false)
    private  String password;

    @ElementCollection
    @Column (name = "rol")
    private Set<String> rol;

    @OneToMany (mappedBy = "user")
    private List<TransactionEntity> transactions;
}
