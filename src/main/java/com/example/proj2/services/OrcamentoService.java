package com.example.proj2.services;

import com.example.proj2.models.Orcamento;
import com.example.proj2.repository.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;

    @Autowired
    public OrcamentoService(OrcamentoRepository orcamentoRepository) {
        this.orcamentoRepository = orcamentoRepository;
    }

    // Método para salvar um novo orçamento com regras de negócio
    public Orcamento salvarOrcamento(Orcamento orcamento) {
        // Validações
        if (orcamento.getDescricao() == null || orcamento.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição é obrigatória.");
        }
        if (orcamento.getValorestimado() == null || orcamento.getValorestimado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor estimado deve ser maior que zero.");
        }
        if (orcamento.getDatacriacao() == null) {
            throw new IllegalArgumentException("A data de criação é obrigatória.");
        }
        if (orcamento.getEstado() == null || orcamento.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("O estado do orçamento é obrigatório.");
        }
        if (orcamento.getProjetostecnicos() == null) {
            throw new IllegalArgumentException("O projeto técnico associado deve ser informado.");
        }
        return orcamentoRepository.save(orcamento);
    }

    // Método para listar todos os orçamentos
    public List<Orcamento> listarOrcamentos() {
        return orcamentoRepository.findAll();
    }

    // Método para buscar um orçamento por ID
    public Optional<Orcamento> buscarOrcamentoPorId(BigDecimal id) {
        return orcamentoRepository.findById(id);
    }

    // Método para atualizar um orçamento
    public Orcamento atualizarOrcamento(Orcamento orcamento) {
        if (orcamento.getId() == null) {
            throw new IllegalArgumentException("O ID do orçamento é obrigatório para atualização.");
        }
        // Outras validações ou regras podem ser aplicadas aqui
        return orcamentoRepository.save(orcamento);
    }

    // Método para remover um orçamento por ID
    public void removerOrcamento(BigDecimal id) {
        orcamentoRepository.deleteById(id);
    }
}
