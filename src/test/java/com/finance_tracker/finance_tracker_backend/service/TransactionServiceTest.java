package com.finance_tracker.finance_tracker_backend.service;

import com.finance_tracker.finance_tracker_backend.config.SecurityConfig;
import com.finance_tracker.finance_tracker_backend.dto.PagesResponseDTO;
import com.finance_tracker.finance_tracker_backend.dto.TransactionResponseDTO;
import com.finance_tracker.finance_tracker_backend.entity.*;
import com.finance_tracker.finance_tracker_backend.repository.RefreshTokenRepository;
import com.finance_tracker.finance_tracker_backend.repository.TransactionRepository;
import com.finance_tracker.finance_tracker_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository; //Objeto falso

    @Mock
    private UserRepository userRepository; //Objeto falso

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private TransactionService transactionService; // el real

    //test
    // el test se componen de tres partes, AAA (Arrange, Act, Assert)
    // Arrange - prepara los datos falsos
    // Act - llama al metodo que se quiere probar
    // Assert - verifica que el resultado es correcto
    @Test
    void listarMovimientos_sinFiltros_debeRetonarLista(){
        // Arrange - preparar los datos
        String email = "ricardo@gmail.com";

        // Simular usuario autenticado
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(email);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserEntity user = new UserEntity();
        user.setEmail(email);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setMonto(new BigDecimal("100"));
        transaction.setCategoria(TipoCategoria.COMIDA);
        transaction.setTipoTransaccion(TipoTransaccion.EGRESO);
        transaction.setUser(user);

        // le decimos al mock que delvolver cuando lo llamen
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(transactionRepository.findByUser(eq(user), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(transaction)));

        // Act - llamar al metodo
        PagesResponseDTO<TransactionResponseDTO> result =
                transactionService.listarMovimientos(null,null,0,10);

        //Assert - verificar el resultado
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

    }
    @Test
    void validarRefreshToken_expirado_debeLanzarExcepcion() {
        // ARRANGE
        RefreshTokenEntity tokenExpirado = new RefreshTokenEntity();
        tokenExpirado.setToken("uuid-expirado");
        tokenExpirado.setExpiryDate(LocalDateTime.now().minusDays(1)); // ya expiró

        when(refreshTokenRepository.findByToken("uuid-expirado"))
                .thenReturn(Optional.of(tokenExpirado));

        // ACT & ASSERT
        assertThrows(RuntimeException.class, () -> {
            refreshTokenService.validarRefreshToken("uuid-expirado");
        });
    }

}