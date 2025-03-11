package com.example.proj2.services;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.repository.GestordeprojetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GestordeprojetoService {

    private final GestordeprojetoRepository gestorRepository;

    @Autowired
    public GestordeprojetoService(GestordeprojetoRepository gestorRepository) {
        this.gestorRepository = gestorRepository;
    }

    // Método para salvar um novo gestor de projeto com regras de negócio
    public Gestordeprojeto salvarGestor(Gestordeprojeto gestor) {
        // Validações de negócio (exemplo: nome e email obrigatórios)
        if (gestor.getNome() == null || gestor.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do gestor é obrigatório.");
        }
        if (gestor.getEmail() == null || gestor.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O email do gestor é obrigatório.");
        }
        // Outras regras podem ser adicionadas conforme necessário

        return gestorRepository.save(gestor);
    }

    // Método para listar todos os gestores
    public List<Gestordeprojeto> listarGestores() {
        return gestorRepository.findAll();
    }

    // Método para buscar um gestor por ID
    public Optional<Gestordeprojeto> buscarGestorPorId(BigDecimal id) {
        return gestorRepository.findById(id);
    }

    // Método para atualizar um gestor
    public Gestordeprojeto atualizarGestor(Gestordeprojeto gestor) {
        if (gestor.getId() == null) {
            throw new IllegalArgumentException("ID do gestor é obrigatório para atualização.");
        }
        // Outras validações ou regras de negócio podem ser aplicadas aqui
        return gestorRepository.save(gestor);
    }

    // Método para remover um gestor por ID
    public void removerGestor(BigDecimal id) {
        gestorRepository.deleteById(id);
    }
}
