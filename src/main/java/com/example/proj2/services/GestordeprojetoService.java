package com.example.proj2.services;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.repository.GestordeprojetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GestordeprojetoService {

    private final GestordeprojetoRepository gestordeprojetoRepository;

    @Autowired
    public GestordeprojetoService(GestordeprojetoRepository gestordeprojetoRepository) {
        this.gestordeprojetoRepository = gestordeprojetoRepository;
    }

    // Método para salvar ou atualizar um gestor de projeto
    public Gestordeprojeto salvarGestor(Gestordeprojeto gestor) {
        if (gestor.getId() != null && gestordeprojetoRepository.existsById(gestor.getId())) {
            // Retorna o objeto já existente para evitar nova inserção
            return gestordeprojetoRepository.findById(gestor.getId()).orElse(gestor);
        }
        return gestordeprojetoRepository.save(gestor);
    }

    // Métodos para buscar, atualizar e remover
    public Optional<Gestordeprojeto> buscarGestorPorId(Long id) {
        return gestordeprojetoRepository.findById(id);
    }

    public List<Gestordeprojeto> listarGestores() {
        return gestordeprojetoRepository.findAll();
    }

    // Método para atualizar um gestor de projeto
    public Gestordeprojeto atualizarGestor(Gestordeprojeto gestor) {
        // Verificar se o ID do gestor está presente, caso contrário lança uma exceção
        if (gestor.getId() == null) {
            throw new IllegalArgumentException("O ID do gestor é obrigatório para efetuar a atualização.");
        }
        return gestordeprojetoRepository.save(gestor);
    }

    // Método para remover um gestor de projeto pelo ID
    public void removerGestor(Long id) {
        gestordeprojetoRepository.deleteById(id);
    }
}

