package com.example.proj2.services;

import com.example.proj2.models.Tipoutilizador;
import com.example.proj2.repository.TipoutilizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoutilizadorService {

    private final TipoutilizadorRepository tipoutilizadorRepository;

    @Autowired
    public TipoutilizadorService(TipoutilizadorRepository tipoutilizadorRepository) {
        this.tipoutilizadorRepository = tipoutilizadorRepository;
    }

    // Método para salvar um novo tipo de utilizador com regras de negócio
    public Tipoutilizador salvarTipoutilizador(Tipoutilizador tipoutilizador) {
        if (tipoutilizador.getDescricao() == null || tipoutilizador.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do tipo de utilizador é obrigatória.");
        }
        return tipoutilizadorRepository.save(tipoutilizador);
    }

    // Método para listar todos os tipos de utilizador
    public List<Tipoutilizador> listarTipoutilizadores() {
        return tipoutilizadorRepository.findAll();
    }

    // Método para buscar um tipo de utilizador por ID
    public Optional<Tipoutilizador> buscarTipoutilizadorPorId(Integer id) {
        return tipoutilizadorRepository.findById(id);
    }

    // Método para atualizar um tipo de utilizador existente
    public Tipoutilizador atualizarTipoutilizador(Tipoutilizador tipoutilizador) {
        if (tipoutilizador.getId() == null) {
            throw new IllegalArgumentException("O ID do tipo de utilizador é obrigatório para atualização.");
        }
        return tipoutilizadorRepository.save(tipoutilizador);
    }

    // Método para remover um tipo de utilizador por ID
    public void removerTipoutilizador(Integer id) {
        tipoutilizadorRepository.deleteById(id);
    }
}
