package com.example.proj2.repository;

import com.example.proj2.models.Licenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface LicencaRepository extends JpaRepository<Licenca, BigDecimal> {
}