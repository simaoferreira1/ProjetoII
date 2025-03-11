package com.example.proj2.repository;

import com.example.proj2.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface PagamentoRepository extends JpaRepository<Pagamento, BigDecimal> {
}