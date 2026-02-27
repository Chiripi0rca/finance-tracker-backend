package com.finance_tracker.finance_tracker_backend.repository;

import com.finance_tracker.finance_tracker_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //crear el metodo findByEmail
    Optional<UserEntity> findByEmail (String email);
}
