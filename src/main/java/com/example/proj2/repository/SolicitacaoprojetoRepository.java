package com.example.proj2.repository;

import com.example.proj2.models.Cliente;
import com.example.proj2.models.Solicitacaoprojeto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitacaoprojetoRepository extends JpaRepository<Solicitacaoprojeto, Integer> {
    List<Solicitacaoprojeto> findByCliente(Cliente cliente);
}
