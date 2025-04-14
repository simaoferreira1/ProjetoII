package com.example.proj2.repository;

import com.example.proj2.models.Membrodepartamentofinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembrodepartamentofinanceiroRepository extends JpaRepository<Membrodepartamentofinanceiro, Long> {
    Membrodepartamentofinanceiro findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}
