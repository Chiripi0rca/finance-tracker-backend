package com.finance_tracker.finance_tracker_backend.service;

import com.finance_tracker.finance_tracker_backend.dto.*;
import com.finance_tracker.finance_tracker_backend.entity.TipoCategoria;
import com.finance_tracker.finance_tracker_backend.entity.TipoTransaccion;
import com.finance_tracker.finance_tracker_backend.entity.TransactionEntity;
import com.finance_tracker.finance_tracker_backend.entity.UserEntity;
import com.finance_tracker.finance_tracker_backend.exception.ResourceNotFoundException;
import com.finance_tracker.finance_tracker_backend.repository.TransactionRepository;
import com.finance_tracker.finance_tracker_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;



@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;


   // @CacheEvict("dashboard")
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

    //@CacheEvict("dashboard")
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


    public void delete(Long id){
        transactionRepository.deleteById(id);
    }

    public PagesResponseDTO<TransactionResponseDTO> listarMovimientos(TipoTransaccion tipoTransaccion,
                                                                      TipoCategoria categoria,
                                                                      LocalDate mes,
                                                                      int page,
                                                                      int size){
        UserEntity user = userRepository.findByEmail(getUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (size >= 30) size = 30;

        Pageable pageable = PageRequest.of(page, size);

        LocalDateTime fin = null;
        LocalDateTime inicio = null;

        if (mes != null) {
            inicio = mes.atStartOfDay();
            fin = inicio.plusMonths(1).minusSeconds(1);
        }

        Page<TransactionEntity> entities = obtenerMovimientosFiltrados(
                user,tipoTransaccion,categoria,inicio,fin,pageable
        );

        return new PagesResponseDTO<>(
               entities.getContent().stream().map(this::convertirAResponseDto).toList(),
               entities.getTotalPages(),
               entities.getTotalElements(),
               entities.getNumber(),
                entities.getSize()
        );
    }

     public Page<TransactionEntity> obtenerMovimientosFiltrados(
             UserEntity user,
             TipoTransaccion tipoTransaccion,
             TipoCategoria categoria,
             LocalDateTime inicio,
             LocalDateTime fin,
             Pageable pageable
     ){
         if (tipoTransaccion != null && categoria != null && inicio != null){
             return transactionRepository.findByUserAndTipoTransaccionAndCategoriaAndCreatedAtBetween(user,
                     tipoTransaccion,
                     categoria,
                     inicio,
                     fin,
                     pageable);
         } else if (tipoTransaccion != null && categoria != null) {
             return transactionRepository.findByUserAndTipoTransaccionAndCategoria(user, tipoTransaccion, categoria,pageable);

         } else if (tipoTransaccion != null && inicio != null){
             return transactionRepository.findByUserAndTipoTransaccionAndCreatedAtBetween(user, tipoTransaccion,inicio,fin,pageable);
         }
         else if (categoria != null && inicio != null) {
             return transactionRepository.findByUserAndCategoriaAndCreatedAtBetween(user, categoria, inicio, fin,pageable);
         } else if (categoria != null) {
             return transactionRepository.findByUserAndCategoria(user, categoria, pageable);
         } else if (inicio != null) {
             return transactionRepository.findByUserAndCreatedAtBetween(user, inicio, fin,pageable);
         } else if (tipoTransaccion != null) {
             return transactionRepository.findByUserAndTipoTransaccion(user,tipoTransaccion,pageable);
         } else {
             return transactionRepository.findByUser(user,pageable);
         }
     }

    //@Cacheable(value = "dashboard", key = "#root.target.getUser()")
    public DashboardDTO getDashboard(){
        UserEntity user = userRepository.findByEmail(getUser())
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
        LocalDateTime inicio = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
        LocalDateTime fin = inicio.plusMonths(1).minusSeconds(1);
        Page<TransactionEntity> page = transactionRepository.findByUserAndCreatedAtBetween(user,inicio, fin, Pageable.unpaged());


        List<TransactionEntity> entities = page.getContent();
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

    public String exportarCSV(){
        UserEntity user = userRepository.findByEmail(getUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Page<TransactionEntity> entities = transactionRepository.findByUser(user, Pageable.unpaged());

        StringBuilder csv = new StringBuilder();

        csv.append("id,monto,categoria,tipo,fecha,actualizacion\n");

        for (TransactionEntity entity :entities.getContent()){
            csv.append(entity.getId()).append(",")
                            .append(entity.getMonto()).append(",")
                            .append(entity.getCategoria()).append(",")
                            .append(entity.getTipoTransaccion()).append(",")
                            .append(entity.getCreatedAt()).append(",")
                            .append(entity.getUpdatedAt()).append("\n");
        }
        return csv.toString();
    }

    public String getUser(){
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }
}
