package com.finance_tracker.finance_tracker_backend.controller;

import com.finance_tracker.finance_tracker_backend.dto.*;
import com.finance_tracker.finance_tracker_backend.entity.TipoCategoria;
import com.finance_tracker.finance_tracker_backend.entity.TipoTransaccion;
import com.finance_tracker.finance_tracker_backend.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Tag(name="Transacciones", description = "Gestion de ingresos y egresos")
@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
       private final TransactionService transactionService;

       @Operation (summary = "Listar todas mis movimientos")
       @GetMapping
       public PagesResponseDTO<TransactionResponseDTO> listarMovimientos(
               @RequestParam(required = false) TipoTransaccion tipoTransaccion,
               @RequestParam(required = false)TipoCategoria categoria,
               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate mes,
               @RequestParam(defaultValue = "0") int page,
               @RequestParam(defaultValue = "10") int size
               ){
           return transactionService.listarMovimientos(tipoTransaccion,categoria,mes,page,size);
       }

       @Operation (summary = "Ver el resumen del mes")
       @GetMapping("/dashboard")
       public ResponseEntity<DashboardDTO> getDashboard(){
           return ResponseEntity.ok(transactionService.getDashboard());
       }

       @Operation (summary = "Crear un nuevo movimiento/transaccion")
       @PostMapping
    public ResponseEntity<TransactionResponseDTO> crear(@RequestBody @Valid CreateTransactionDTO createDTO){
           return ResponseEntity.ok(transactionService.crear(createDTO));
       }

       @Operation (summary = "Actualizar un movimiento/transaccion")
       @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> actualizar(@RequestBody @Valid UpdateTransactionDTO updateDTO, @PathVariable Long id){
           return ResponseEntity.ok(transactionService.actualizar(updateDTO,id));
       }

       @Operation (summary = "Eliminar un movimiento/transaccion")
       @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id){
           transactionService.delete(id);
       }

       @Operation (summary = "Expotar transacciones como archivo Excel")
       @GetMapping ("/export/csv")
       public ResponseEntity<String> exportarCSV(){
           String csv = transactionService.exportarCSV();
           return ResponseEntity.ok()
                   .header("Content-Disposition", "attachment; filename=transacciones.csv")
                   .contentType(MediaType.parseMediaType("text/csv"))
                   .body(csv);
       }

}
