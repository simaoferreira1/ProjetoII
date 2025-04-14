package com.example.proj2.repository;

import com.example.proj2.models.Gestordeprojeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface GestordeprojetoRepository extends JpaRepository<Gestordeprojeto, Long> {
    Gestordeprojeto findByEmailAndPassword(String email, String password); // <- AQUI
}
