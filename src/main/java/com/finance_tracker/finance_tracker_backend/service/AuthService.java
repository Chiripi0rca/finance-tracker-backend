package com.finance_tracker.finance_tracker_backend.service;

import com.finance_tracker.finance_tracker_backend.dto.AuthResponseDTO;
import com.finance_tracker.finance_tracker_backend.dto.LoginRequestDTO;
import com.finance_tracker.finance_tracker_backend.dto.RegisterRequestDTO;
import com.finance_tracker.finance_tracker_backend.entity.RefreshTokenEntity;
import com.finance_tracker.finance_tracker_backend.entity.UserEntity;
import com.finance_tracker.finance_tracker_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;
    private final RefreshTokenService refreshTokenService;


    //este metodo recibe como parametro un "RegisterRequestDTO"(email y password)
    // y devuelve un "AuthResponseDTO" (email y token)
    public AuthResponseDTO register(RegisterRequestDTO requestDTO) {
         if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()){
             throw new RuntimeException("Este correo ya existe");
         }


        UserEntity entity = new UserEntity(); //aqui creamos un UserEntity para guardar los datos del usuario
        entity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));//aqui encriptamos la password con
        entity.setEmail(requestDTO.getEmail());
        entity.setRol(Set.of("ROLE_USER"));
        UserEntity userSave = userRepository.save(entity);//aqui aguardamos la entity en la DB
        AuthResponseDTO responseDTO = new AuthResponseDTO();//aqui creamos el response
        responseDTO.setEmail(userSave.getEmail());
        responseDTO.setToken(jwtService.generateToken(userSave.getEmail()));//aqui se genera el token
        return responseDTO;
    }


    //este metodo autentica al usuario
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
             manager.authenticate(new UsernamePasswordAuthenticationToken(
                     loginRequestDTO.getEmail()
                     ,loginRequestDTO.getPassword())
                     );//aqui el usario ya esta registrado y si no lo esta nos lanzara un exception
             UserEntity user = userRepository.findByEmail(loginRequestDTO.getEmail())
                     .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                 AuthResponseDTO responseDTO = new AuthResponseDTO();
                 responseDTO.setEmail(loginRequestDTO.getEmail());
                 responseDTO.setToken(jwtService.generateToken(loginRequestDTO.getEmail()));
                 responseDTO.setRefreshToken(refreshTokenService.crearRefreshToken(user).getToken());
                 return responseDTO;
    }

    public AuthResponseDTO refresh (String refreshToken){
        RefreshTokenEntity token = refreshTokenService.validarRefreshToken(refreshToken);

        UserEntity user = token.getUser();

        AuthResponseDTO responseDTO = new AuthResponseDTO();
        responseDTO.setEmail(user.getEmail());
        responseDTO.setToken(jwtService.generateToken(user.getEmail()));
        responseDTO.setRefreshToken(refreshToken);
        return  responseDTO;
    }


}
