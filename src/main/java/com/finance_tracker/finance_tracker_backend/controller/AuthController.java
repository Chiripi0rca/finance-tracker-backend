package com.finance_tracker.finance_tracker_backend.controller;


import com.finance_tracker.finance_tracker_backend.dto.AuthResponseDTO;
import com.finance_tracker.finance_tracker_backend.dto.LoginRequestDTO;
import com.finance_tracker.finance_tracker_backend.dto.RegisterRequestDTO;
import com.finance_tracker.finance_tracker_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    //usaremos "ResponseEntity" para envolver toda la respuesta HTTP
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO requestDTO){
        return ResponseEntity.ok(authService.register(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login (@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }
}
