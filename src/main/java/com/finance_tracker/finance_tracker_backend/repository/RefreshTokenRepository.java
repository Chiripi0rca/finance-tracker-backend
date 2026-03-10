package com.finance_tracker.finance_tracker_backend.repository;

import com.finance_tracker.finance_tracker_backend.entity.RefreshTokenEntity;
import com.finance_tracker.finance_tracker_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken (String token);
    Optional<RefreshTokenEntity> findByUser (UserEntity user);
}
