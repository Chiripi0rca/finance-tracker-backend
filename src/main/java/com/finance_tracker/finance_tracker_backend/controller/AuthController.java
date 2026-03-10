package com.finance_tracker.finance_tracker_backend.controller;


import com.finance_tracker.finance_tracker_backend.dto.AuthResponseDTO;
import com.finance_tracker.finance_tracker_backend.dto.LoginRequestDTO;
import com.finance_tracker.finance_tracker_backend.dto.RefreshTokenRequestDTO;
import com.finance_tracker.finance_tracker_backend.dto.RegisterRequestDTO;
import com.finance_tracker.finance_tracker_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticacion", description = "Registro e inicio de sesion")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    //usaremos "ResponseEntity" para envolver toda la respuesta HTTP
    @Operation(summary = "Regisrar nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO requestDTO){
        return ResponseEntity.ok(authService.register(requestDTO));
    }

    @Operation (summary = "Iniciar sesion y obtner el token JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login (@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @Operation (summary = "Obtner nuevo access token con refresh token")
    @PostMapping ("refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO tokenRequestDTO){
        return ResponseEntity.ok(authService.refresh(tokenRequestDTO.getRefreshToken()));
    }
}
