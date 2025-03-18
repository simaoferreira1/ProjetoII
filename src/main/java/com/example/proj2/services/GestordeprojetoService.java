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

    @Autowired
    private GestordeprojetoRepository gestordeprojetoRepository;

    public Gestordeprojeto salvarGestor(Gestordeprojeto gestor) {
        return gestordeprojetoRepository.save(gestor);
    }

    public Optional<Gestordeprojeto> buscarGestorPorId(BigDecimal id) {
        return gestordeprojetoRepository.findById(id);
    }

    public List<Gestordeprojeto> listarGestores() {
        return gestordeprojetoRepository.findAll();
    }

    public Gestordeprojeto atualizarGestor(Gestordeprojeto gestor) {
        return gestordeprojetoRepository.save(gestor);
    }

    public void removerGestor(BigDecimal id) {
        gestordeprojetoRepository.deleteById(id);
    }
}
