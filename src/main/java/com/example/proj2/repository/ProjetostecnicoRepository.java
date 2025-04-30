package com.example.proj2.repository;

import com.example.proj2.models.Projetostecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProjetostecnicoRepository extends JpaRepository<Projetostecnico, Integer> {
}