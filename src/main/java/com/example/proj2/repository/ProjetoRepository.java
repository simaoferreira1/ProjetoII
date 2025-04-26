package com.example.proj2.repository;

import com.example.proj2.models.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, BigDecimal> {
    List<Projeto> findByEstado(String estado);

    @Query("SELECT p FROM Projeto p LEFT JOIN FETCH p.gestordeprojeto")
    List<Projeto> findAllWithGestordeprojeto();
}