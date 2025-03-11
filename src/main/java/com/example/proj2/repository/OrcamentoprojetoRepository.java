package com.example.proj2.repository;

import com.example.proj2.models.Orcamentoprojeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface OrcamentoprojetoRepository extends JpaRepository<Orcamentoprojeto, BigDecimal> {
}