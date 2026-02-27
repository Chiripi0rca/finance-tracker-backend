package com.finance_tracker.finance_tracker_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequestDTO {

    @NotBlank(message = "El emial es obligatorio")
    private String email;


    @NotBlank(message = "La contrasena es obligatoria")
    private String password;
}
