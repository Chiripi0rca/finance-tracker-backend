package com.finance_tracker.finance_tracker_backend.service;

import com.finance_tracker.finance_tracker_backend.dto.CreateTransactionDTO;
import com.finance_tracker.finance_tracker_backend.dto.DashboardDTO;
import com.finance_tracker.finance_tracker_backend.dto.TransactionResponseDTO;
import com.finance_tracker.finance_tracker_backend.dto.UpdateTransactionDTO;
import com.finance_tracker.finance_tracker_backend.entity.TipoTransaccion;
import com.finance_tracker.finance_tracker_backend.entity.TransactionEntity;
import com.finance_tracker.finance_tracker_backend.entity.UserEntity;
import com.finance_tracker.finance_tracker_backend.exception.ResourceNotFoundException;
import com.finance_tracker.finance_tracker_backend.repository.TransactionRepository;
import com.finance_tracker.finance_tracker_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;



@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @CacheEvict("dashboard")
    public TransactionResponseDTO crear(CreateTransactionDTO createTransactionDTO){
        UserEntity user = userRepository.findByEmail(getUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        TransactionEntity entity = new TransactionEntity();
        entity.setCreatedAt(LocalDateTime.now());
        entity.setMonto(createTransactionDTO.getMonto());
        entity.setCategoria(createTransactionDTO.getCategoria());
        entity.setDescripcion(createTransactionDTO.getDescripcion());
        entity.setTipoTransaccion(createTransactionDTO.getTipoTransaccion());
        entity.setUser(user);
        TransactionEntity transactionSave = transactionRepository.save(entity);

        return convertirAResponseDto(transactionSave);
    }

    @CacheEvict("dashboard")
    public TransactionResponseDTO actualizar(UpdateTransactionDTO updateDTO, Long id){

        Optional<TransactionEntity> optional = transactionRepository.findById(id);
        if(optional.isEmpty()){
            throw new ResourceNotFoundException("Movimiento no encontrado");
        }
        TransactionEntity entity = optional.get();

        if (updateDTO.getTipoTransaccion() != null){
            entity.setTipoTransaccion(updateDTO.getTipoTransaccion());
        }
        if (updateDTO.getCategoria() != null){
            entity.setCategoria(updateDTO.getCategoria());
        }
        if (updateDTO.getMonto() != null){
            entity.setMonto(updateDTO.getMonto());
        }
        if (updateDTO.getDescripcion() != null){
            entity.setDescripcion(updateDTO.getDescripcion());
        }

        entity.setUpdatedAt(LocalDateTime.now());

        TransactionEntity update = transactionRepository.save(entity);
        return convertirAResponseDto(update);
    }
    public List<TransactionResponseDTO> obtenerPorID(Long userId){
        List<TransactionEntity> entities = transactionRepository.findByUserId(userId);
        List<TransactionResponseDTO> responseDTOList = new ArrayList<>();

        for (TransactionEntity entity : entities){
            responseDTOList.add(convertirAResponseDto(entity));
        }

        return responseDTOList;
    }

    public void delete(Long id){
        transactionRepository.deleteById(id);
    }

    public List<TransactionResponseDTO> listarMovimientos(){
        UserEntity user = userRepository.findByEmail(getUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<TransactionEntity> entities = transactionRepository.findByUser(user);
        List<TransactionResponseDTO> responseDTOList = new ArrayList<>();

        for (TransactionEntity entity : entities){
            responseDTOList.add(convertirAResponseDto(entity));
        }
        return responseDTOList;
    }

    @Cacheable(value = "dashboard", key = "#root.target.getUser()")
    public DashboardDTO getDashboard(){
        UserEntity user = userRepository.findByEmail(getUser())
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        List<TransactionEntity> entities = transactionRepository.findByUser(user);

        LocalDateTime inicio = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
        entities = entities.stream()
                .filter(entity -> entity.getCreatedAt().isAfter(inicio))
                .toList();
        BigDecimal totalEgresos = BigDecimal.ZERO;
        BigDecimal totalIngresos = BigDecimal.ZERO;
        BigDecimal calcularBalance;
        for (TransactionEntity entity : entities){
            if (entity.getTipoTransaccion() == TipoTransaccion.EGRESO) {
                totalEgresos = totalEgresos.add(entity.getMonto());
            } else {
                totalIngresos = totalIngresos.add(entity.getMonto());
            }
        }

        calcularBalance = totalIngresos.subtract(totalEgresos);
        return new DashboardDTO(totalIngresos, totalEgresos, calcularBalance);
    }

    public TransactionResponseDTO convertirAResponseDto(TransactionEntity entity){
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();
        responseDTO.setCreatedAt(entity.getCreatedAt());
        responseDTO.setUpdatedAt(entity.getUpdatedAt());
        responseDTO.setCategoria(entity.getCategoria());
        responseDTO.setMonto(entity.getMonto());
        responseDTO.setId(entity.getId());
        responseDTO.setDescripcion(entity.getDescripcion());
        responseDTO.setTipoTransaccion(entity.getTipoTransaccion());
        return responseDTO;

    }

    public String getUser(){
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }
}
