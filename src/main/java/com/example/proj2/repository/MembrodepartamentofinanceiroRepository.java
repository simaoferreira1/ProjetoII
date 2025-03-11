package com.example.proj2.repository;

import com.example.proj2.models.Membrodepartamentofinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface MembrodepartamentofinanceiroRepository extends JpaRepository<Membrodepartamentofinanceiro, BigDecimal> {
}