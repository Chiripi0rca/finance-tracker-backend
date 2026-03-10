package com.finance_tracker.finance_tracker_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagesResponseDTO<T>{

    private List<T> content;  //las transacciones de esta pagina
    private int totalPages;
    private Long totalElements;
    private int currentPage;
    private int size;
}
