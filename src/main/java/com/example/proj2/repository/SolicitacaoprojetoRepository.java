package com.example.proj2.repository;

import com.example.proj2.models.Solicitacaoprojeto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;

public interface SolicitacaoprojetoRepository extends JpaRepository<Solicitacaoprojeto, Integer> {
}