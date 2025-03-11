package com.example.proj2.repository;

import com.example.proj2.models.Gestordeprojeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface GestordeprojetoRepository extends JpaRepository<Gestordeprojeto, BigDecimal> {
}