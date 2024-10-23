package com.example.API_Running.repository;

import com.example.API_Running.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername (String username);

    Optional<User> findByMail (String mail);

}
