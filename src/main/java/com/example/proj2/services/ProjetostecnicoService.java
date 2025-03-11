package com.example.proj2.services;

import com.example.proj2.models.Projetostecnico;
import com.example.proj2.repository.ProjetostecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjetostecnicoService {

    private final ProjetostecnicoRepository projetostecnicoRepository;

    @Autowired
    public ProjetostecnicoService(ProjetostecnicoRepository projetostecnicoRepository) {
        this.projetostecnicoRepository = projetostecnicoRepository;
    }

    // Método para salvar um novo projeto técnico com regras de negócio
    public Projetostecnico salvarProjetostecnico(Projetostecnico projetostecnico) {
        // Validação: Nome é obrigatório
        if (projetostecnico.getNome() == null || projetostecnico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do projeto técnico é obrigatório.");
        }
        // Validação: Descrição é obrigatória
        if (projetostecnico.getDescricao() == null || projetostecnico.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do projeto técnico é obrigatória.");
        }
        // Validação: Data de criação é obrigatória
        if (projetostecnico.getDatacriacao() == null) {
            throw new IllegalArgumentException("A data de criação do projeto técnico é obrigatória.");
        }
        // Validação: Especialista associado é obrigatório
        if (projetostecnico.getEspecialista() == null) {
            throw new IllegalArgumentException("O especialista associado é obrigatório.");
        }
        return projetostecnicoRepository.save(projetostecnico);
    }

    // Método para listar todos os projetos técnicos
    public List<Projetostecnico> listarProjetostecnicos() {
        return projetostecnicoRepository.findAll();
    }

    // Método para buscar um projeto técnico por ID
    public Optional<Projetostecnico> buscarProjetostecnicoPorId(BigDecimal id) {
        return projetostecnicoRepository.findById(id);
    }

    // Método para atualizar um projeto técnico
    public Projetostecnico atualizarProjetostecnico(Projetostecnico projetostecnico) {
        if (projetostecnico.getId() == null) {
            throw new IllegalArgumentException("O ID do projeto técnico é obrigatório para atualização.");
        }
        // Outras validações podem ser adicionadas conforme necessário
        return projetostecnicoRepository.save(projetostecnico);
    }

    // Método para remover um projeto técnico por ID
    public void removerProjetostecnico(BigDecimal id) {
        projetostecnicoRepository.deleteById(id);
    }
}
