package com.example.API_Running.repository;

import com.example.API_Running.models.PolarAccount;
import com.example.API_Running.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolarAccountRepository extends JpaRepository<PolarAccount,Long> {
    Optional<PolarAccount> findByUser(User user);
}