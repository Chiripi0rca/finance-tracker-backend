package com.finance_tracker.finance_tracker_backend.dto;


import com.finance_tracker.finance_tracker_backend.entity.TipoTransaccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO {

    @NotNull(message = "Introduzca el monto de su movimiento")
    private BigDecimal monto;

    @NotBlank(message = "Introduzca la categoria de su movimiento")
    private String categoria;

    @Size(max=100, message = "la descripcion supera mas de 100 caracteres")
    private String descripcion;

    @NotNull(message = "Eliga el tipo de su movimiento EGRESO/INGRESO")
    private TipoTransaccion tipoTransaccion;
}
