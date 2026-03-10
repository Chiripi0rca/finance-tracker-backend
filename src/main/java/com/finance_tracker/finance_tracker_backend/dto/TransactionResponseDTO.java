package com.finance_tracker.finance_tracker_backend.dto;

import com.finance_tracker.finance_tracker_backend.entity.TipoCategoria;
import com.finance_tracker.finance_tracker_backend.entity.TipoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    private Long id;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private BigDecimal monto;
    private TipoCategoria categoria;
    private String descripcion;
    private TipoTransaccion tipoTransaccion;
}
