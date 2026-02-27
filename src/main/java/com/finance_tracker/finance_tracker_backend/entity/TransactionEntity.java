package com.finance_tracker.finance_tracker_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table (name = "transactions")
@Data
public class TransactionEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "createdAt")
    private LocalDateTime createdAt;

    @Column (name = "monto")
    private BigDecimal monto;

    @Column (name = "categoria")
    private String categoria;

    @Column (name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipoTransaccion;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private UserEntity user;
}
