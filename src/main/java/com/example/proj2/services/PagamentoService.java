package com.example.proj2.services;

import com.example.proj2.models.Pagamento;
import com.example.proj2.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    // Método para salvar um novo pagamento com regras de negócio
    public Pagamento salvarPagamento(Pagamento pagamento) {
        // Validação: o valor do pagamento deve ser informado e maior que zero.
        if (pagamento.getValor() == null || pagamento.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero.");
        }
        // Validação: a data do pagamento deve ser informada.
        if (pagamento.getDatapagamento() == null) {
            throw new IllegalArgumentException("A data do pagamento é obrigatória.");
        }
        // Validação: o cliente associado deve ser informado.
        if (pagamento.getCliente() == null) {
            throw new IllegalArgumentException("O cliente associado ao pagamento deve ser informado.");
        }
        return pagamentoRepository.save(pagamento);
    }

    // Método para listar todos os pagamentos
    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll();
    }

    // Método para buscar um pagamento por ID
    public Optional<Pagamento> buscarPagamentoPorId(BigDecimal id) {
        return pagamentoRepository.findById(id);
    }

    // Método para atualizar um pagamento
    public Pagamento atualizarPagamento(Pagamento pagamento) {
        if (pagamento.getId() == null) {
            throw new IllegalArgumentException("O ID do pagamento é obrigatório para atualização.");
        }
        // Outras regras de negócio podem ser adicionadas aqui, se necessário.
        return pagamentoRepository.save(pagamento);
    }

    // Método para remover um pagamento por ID
    public void removerPagamento(BigDecimal id) {
        pagamentoRepository.deleteById(id);
    }
}
