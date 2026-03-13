package com.finance_tracker.finance_tracker_backend.repository;


import com.finance_tracker.finance_tracker_backend.entity.TipoCategoria;
import com.finance_tracker.finance_tracker_backend.entity.TipoTransaccion;
import com.finance_tracker.finance_tracker_backend.entity.TransactionEntity;
import com.finance_tracker.finance_tracker_backend.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
        Page<TransactionEntity> findByUser(UserEntity user, Pageable pageable);

        Page<TransactionEntity> findByUserAndCreatedAtBetween(UserEntity user,
                                                              LocalDateTime createdAtAfter,
                                                              LocalDateTime createdAtBefore,
                                                              Pageable pageable);

        Page<TransactionEntity> findByUserAndCategoria(UserEntity user,
                                                       TipoCategoria categoria,
                                                       Pageable pageable);

        Page<TransactionEntity> findByUserAndCategoriaAndCreatedAtBetween(UserEntity user,
                                                                          TipoCategoria categoria,
                                                                          LocalDateTime createdAtAfter,
                                                                          LocalDateTime createdAtBefore,
                                                                          Pageable pageable);

        Page<TransactionEntity> findByUserAndTipoTransaccionAndCategoriaAndCreatedAtBetween(UserEntity user,
                                                                                            TipoTransaccion tipoTransaccion,
                                                                                            TipoCategoria categoria,
                                                                                            LocalDateTime createdAtAfter,
                                                                                            LocalDateTime createdAtBefore,
                                                                                            Pageable pageable);

        Page<TransactionEntity> findByUserAndTipoTransaccionAndCategoria(UserEntity user,
                                                                         TipoTransaccion tipoTransaccion,
                                                                         TipoCategoria categoria,
                                                                         Pageable pageable);

        Page<TransactionEntity> findByUserAndTipoTransaccionAndCreatedAtBetween(UserEntity user,
                                                                             TipoTransaccion tipoTransaccion,
                                                                             LocalDateTime CretedAtAfter,
                                                                             LocalDateTime CreatedAtBefore,
                                                                             Pageable pageable);

        Page<TransactionEntity> findByUserAndTipoTransaccion(UserEntity user, TipoTransaccion tipoTransaccion, Pageable pageable);


}