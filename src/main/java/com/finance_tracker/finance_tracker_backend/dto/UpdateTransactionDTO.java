package com.finance_tracker.finance_tracker_backend.dto;

import com.finance_tracker.finance_tracker_backend.entity.TipoCategoria;
import com.finance_tracker.finance_tracker_backend.entity.TipoTransaccion;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionDTO {
    private LocalDateTime updatedAt;

    @Positive(message = "El monto debe de ser mayor a cero")
    private BigDecimal monto;

    private TipoCategoria categoria;

    @Size(max=100, message = "la descripcion supera mas de 100 caracteres")
    private String descripcion;

    private TipoTransaccion tipoTransaccion;
}
