package com.finance_tracker.finance_tracker_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequestDTO {

    @NotBlank(message = "El emial es obligatorio")
    private String email;


    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 6, max = 100, message = " La contrasena debe de tener un minimo de 6 caracteres")
    private String password;

}
