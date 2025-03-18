package com.example.proj2.repository;

import com.example.proj2.models.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface EspecialistaRepository extends JpaRepository<Especialista, BigDecimal> {
}
