package com.example.proj2.repository;

import com.example.proj2.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, BigDecimal> {
    // Verifica se já existe um cliente com o e-mail fornecido
    boolean existsByEmail(String email);
}
