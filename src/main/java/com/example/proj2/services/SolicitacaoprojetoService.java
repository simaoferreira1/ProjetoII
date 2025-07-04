package com.example.proj2.services;

import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.repository.SolicitacaoprojetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SolicitacaoprojetoService {

    private final SolicitacaoprojetoRepository solicitacaoprojetoRepository;

    @Autowired
    public SolicitacaoprojetoService(SolicitacaoprojetoRepository solicitacaoprojetoRepository) {
        this.solicitacaoprojetoRepository = solicitacaoprojetoRepository;
    }

    // Método para salvar uma nova solicitação de projeto
    public Solicitacaoprojeto salvarSolicitacaoprojeto(Solicitacaoprojeto solicitacao) {
        if (solicitacao.getDatasolicitacao() == null) {
            throw new IllegalArgumentException("A data da solicitação é obrigatória.");
        }
        if (solicitacao.getEstado() == null || solicitacao.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("O estado da solicitação é obrigatório.");
        }
        if (solicitacao.getCliente() == null) {
            throw new IllegalArgumentException("O cliente associado à solicitação deve ser informado.");
        }
        return solicitacaoprojetoRepository.save(solicitacao);
    }

    // Método para listar todas as solicitações de projeto
    public List<Solicitacaoprojeto> listarSolicitacoes() {
        return solicitacaoprojetoRepository.findAll();
    }

    // Método para buscar uma solicitação por ID
    public Optional<Solicitacaoprojeto> buscarSolicitacaoPorId(Integer id) {
        return solicitacaoprojetoRepository.findById(id);
    }

    // Método para atualizar uma solicitação existente
    public Solicitacaoprojeto atualizarSolicitacao(Solicitacaoprojeto solicitacao) {
        if (solicitacao.getId() == null) {
            throw new IllegalArgumentException("O ID da solicitação é obrigatório para atualização.");
        }
        return solicitacaoprojetoRepository.save(solicitacao);
    }

    // Método para eliminar uma solicitação por ID
    public void eliminarSolicitacao(Integer id) {
        solicitacaoprojetoRepository.deleteById(id);
    }
}
