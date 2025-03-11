package com.example.proj2.repository;

import com.example.proj2.models.Tipoutilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface TipoutilizadorRepository extends JpaRepository<Tipoutilizador, BigDecimal> {
}