package com.example.proj2.repository;

import com.example.proj2.models.Orcamentoprojeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrcamentoprojetoRepository extends JpaRepository<Orcamentoprojeto, Integer> {

    @Query("SELECT o FROM Orcamentoprojeto o LEFT JOIN FETCH o.projeto p LEFT JOIN FETCH p.gestordeprojeto WHERE o.estado = 'pendente de orçamento'")
    List<Orcamentoprojeto> findAllPendingWithProjetoAndGestordeprojeto();
}