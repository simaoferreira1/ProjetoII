package com.example.proj2.services;

import com.example.proj2.models.Projeto;
import com.example.proj2.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    @Autowired
    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    // Método para salvar um novo projeto com regras de negócio
    public Projeto salvarProjeto(Projeto projeto) {
        // Validação: nome obrigatório
        if (projeto.getNome() == null || projeto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do projeto é obrigatório.");
        }
        // Validação: data de início obrigatória
        if (projeto.getDatainicio() == null) {
            throw new IllegalArgumentException("A data de início do projeto é obrigatória.");
        }
        // Validação: data fim prevista obrigatória
        if (projeto.getDatafimprevista() == null) {
            throw new IllegalArgumentException("A data fim prevista é obrigatória.");
        }
        // Validação: data fim prevista deve ser posterior à data de início
        if (projeto.getDatafimprevista().isBefore(projeto.getDatainicio())) {
            throw new IllegalArgumentException("A data fim prevista deve ser posterior à data de início.");
        }
        // Validação: Gestor do projeto (Gestordeprojeto) é obrigatório
        if (projeto.getGestordeprojeto() == null) {
            throw new IllegalArgumentException("O gestor do projeto é obrigatório.");
        }
        // Outras validações podem ser aplicadas conforme as necessidades do negócio

        return projetoRepository.save(projeto);
    }

    // Método para listar todos os projetos
    public List<Projeto> listarProjetos() {
        return projetoRepository.findAllWithGestordeprojeto();
    }

    // Método para buscar um projeto por ID
    public Optional<Projeto> buscarProjetoPorId(BigDecimal id) {
        return projetoRepository.findById(id);
    }

    // Método para atualizar um projeto existente
    public Projeto atualizarProjeto(Projeto projeto) {
        if (projeto.getId() == null) {
            throw new IllegalArgumentException("O ID do projeto é obrigatório para atualização.");
        }
        return projetoRepository.save(projeto);
    }

    // Método para remover um projeto por ID
    public void removerProjeto(BigDecimal id) {
        projetoRepository.deleteById(id);
    }

    public List<Projeto> listarProjetosPorEstadoComCliente(String estado) {
        return projetoRepository.findByEstadoWithCliente(estado);
    }

}