package com.example.proj2.repository;

import com.example.proj2.models.Cliente;
import com.example.proj2.models.Solicitacaoprojeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoprojetoRepository extends JpaRepository<Solicitacaoprojeto, Integer> {

    List<Solicitacaoprojeto> findByCliente(Cliente cliente); // ✅ Método necessário
}
