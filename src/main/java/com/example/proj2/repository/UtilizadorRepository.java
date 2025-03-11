package com.example.proj2.repository;

import com.example.proj2.models.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface UtilizadorRepository extends JpaRepository<Utilizador, BigDecimal> {
}