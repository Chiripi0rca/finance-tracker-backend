package com.finance_tracker.finance_tracker_backend.repository;


import com.finance_tracker.finance_tracker_backend.entity.TransactionEntity;
import com.finance_tracker.finance_tracker_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
        List<TransactionEntity> findByUser(UserEntity user);

}
