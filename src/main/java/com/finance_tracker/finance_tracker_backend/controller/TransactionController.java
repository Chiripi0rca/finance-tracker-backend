package com.finance_tracker.finance_tracker_backend.controller;

import com.finance_tracker.finance_tracker_backend.dto.CreateTransactionDTO;
import com.finance_tracker.finance_tracker_backend.dto.DashboardDTO;
import com.finance_tracker.finance_tracker_backend.dto.TransactionResponseDTO;
import com.finance_tracker.finance_tracker_backend.dto.UpdateTransactionDTO;
import com.finance_tracker.finance_tracker_backend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
       private final TransactionService transactionService;


       @GetMapping
       public List<TransactionResponseDTO> listarMovimientos(){
           return transactionService.listarMovimientos();
       }

       @GetMapping("/{id}")
       public List<TransactionResponseDTO> obtnerPorId(@PathVariable Long id){
           return Collections.singletonList(transactionService.obtenerPorID(id));
       }

       @GetMapping("/dashboard")
       public ResponseEntity<DashboardDTO> getDashboard(){
           return ResponseEntity.ok(transactionService.getDashboard());
       }

       @PostMapping
    public ResponseEntity<TransactionResponseDTO> crear(@RequestBody @Valid CreateTransactionDTO createDTO){
           return ResponseEntity.ok(transactionService.crear(createDTO));
       }

       @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> actualizar(@RequestBody @Valid UpdateTransactionDTO updateDTO, @PathVariable Long id){
           return ResponseEntity.ok(transactionService.actualizar(updateDTO,id));
       }

       @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id){
           transactionService.delete(id);
       }



}
