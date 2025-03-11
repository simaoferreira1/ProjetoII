package com.example.proj2.services;

import com.example.proj2.models.Orcamentoprojeto;
import com.example.proj2.repository.OrcamentoprojetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrcamentoprojetoService {

    private final OrcamentoprojetoRepository orcamentoprojetoRepository;

    @Autowired
    public OrcamentoprojetoService(OrcamentoprojetoRepository orcamentoprojetoRepository) {
        this.orcamentoprojetoRepository = orcamentoprojetoRepository;
    }

    // Método para salvar um novo Orcamentoprojeto com regras de negócio
    public Orcamentoprojeto salvarOrcamentoprojeto(Orcamentoprojeto orcamentoprojeto) {
        // Validação: O valor total deve ser informado e ser maior ou igual a zero.
        if (orcamentoprojeto.getValortotal() == null || orcamentoprojeto.getValortotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor total deve ser informado e ser maior ou igual a zero.");
        }
        // Validação: Data de aprovação deve ser informada.
        if (orcamentoprojeto.getDataaprovacao() == null) {
            throw new IllegalArgumentException("A data de aprovação é obrigatória.");
        }
        // Validação: Estado deve ser informado.
        if (orcamentoprojeto.getEstado() == null || orcamentoprojeto.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("O estado do orçamento é obrigatório.");
        }
        // Validação: O projeto principal (campo 'projeto') deve estar definido.
        if (orcamentoprojeto.getProjeto() == null) {
            throw new IllegalArgumentException("O projeto principal associado é obrigatório.");
        }
        // Outras regras de negócio podem ser adicionadas conforme necessário.

        return orcamentoprojetoRepository.save(orcamentoprojeto);
    }

    // Método para listar todos os Orcamentoprojeto
    public List<Orcamentoprojeto> listarOrcamentoprojetos() {
        return orcamentoprojetoRepository.findAll();
    }

    // Método para buscar um Orcamentoprojeto por ID
    public Optional<Orcamentoprojeto> buscarPorId(BigDecimal id) {
        return orcamentoprojetoRepository.findById(id);
    }

    // Método para atualizar um Orcamentoprojeto existente
    public Orcamentoprojeto atualizarOrcamentoprojeto(Orcamentoprojeto orcamentoprojeto) {
        if (orcamentoprojeto.getId() == null) {
            throw new IllegalArgumentException("O ID do orçamento do projeto é obrigatório para atualização.");
        }
        // As mesmas validações do método de salvar podem ser aplicadas aqui se necessário.
        return orcamentoprojetoRepository.save(orcamentoprojeto);
    }

    // Método para remover um Orcamentoprojeto por ID
    public void removerOrcamentoprojeto(BigDecimal id) {
        orcamentoprojetoRepository.deleteById(id);
    }
}
