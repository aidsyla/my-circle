package com.example.androidbackend.repository;

import com.example.androidbackend.models.Register;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
@Transactional
public interface RegisterRepository extends JpaRepository<Register, Integer> {
    Optional<Register> findByEmailOrUsername(String email, String username);
    Optional<Register> findByUsername(String username);

    Optional<Register> findByName(String name);
}
