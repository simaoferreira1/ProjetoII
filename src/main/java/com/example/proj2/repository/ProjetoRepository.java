package com.example.proj2.repository;

import com.example.proj2.models.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

    List<Projeto> findByEstado(String estado);

    @Query("SELECT p FROM Projeto p LEFT JOIN FETCH p.gestordeprojeto")
    List<Projeto> findAllWithGestordeprojeto();

    @Query("SELECT p FROM Projeto p " +
            "LEFT JOIN FETCH p.idcliente " +
            "LEFT JOIN FETCH p.gestordeprojeto " +
            "WHERE p.estado = :estado")
    List<Projeto> findByEstadoWithCliente(@Param("estado") String estado);
}
