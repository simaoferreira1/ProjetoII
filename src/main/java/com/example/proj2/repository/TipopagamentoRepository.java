package com.example.proj2.repository;

import com.example.proj2.models.Tipopagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface TipopagamentoRepository extends JpaRepository<Tipopagamento, BigDecimal> {
}