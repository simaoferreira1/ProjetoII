package com.example.proj2.repository;

import com.example.proj2.models.Tipoespecialista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface TipoespecialistaRepository extends JpaRepository<Tipoespecialista, Integer> {
}