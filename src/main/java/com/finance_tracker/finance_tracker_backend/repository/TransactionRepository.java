package com.finance_tracker.finance_tracker_backend.repository;


import com.finance_tracker.finance_tracker_backend.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
