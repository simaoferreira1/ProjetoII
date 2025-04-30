package com.example.proj2.repository;

import com.example.proj2.models.Cliente;
import com.example.proj2.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    List<Pagamento> findByCliente(Cliente cliente);
}