package com.example.proj2.repository;

import com.example.proj2.models.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Integer> {
}