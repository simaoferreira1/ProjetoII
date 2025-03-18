package com.example.proj2.services;

import com.example.proj2.models.Utilizador;
import com.example.proj2.repository.UtilizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UtilizadorService {

    private final UtilizadorRepository utilizadorRepository;

    @Autowired
    public UtilizadorService(UtilizadorRepository utilizadorRepository) {
        this.utilizadorRepository = utilizadorRepository;
    }

    // Método para salvar um novo utilizador com regras de negócio
    public Utilizador salvarUtilizador(Utilizador utilizador) {
        if (utilizador.getNome() == null || utilizador.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do utilizador é obrigatório.");
        }
        if (utilizador.getUser() == null || utilizador.getUser().trim().isEmpty()) {
            throw new IllegalArgumentException("O username é obrigatório.");
        }
        if (utilizador.getPassword() == null || utilizador.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("A password é obrigatória.");
        }
        if (utilizador.getIdtipoutilizador() == null) {
            throw new IllegalArgumentException("O tipo de utilizador é obrigatório.");
        }
        return utilizadorRepository.save(utilizador);
    }

    // Método para listar todos os utilizadores
    public List<Utilizador> listarUtilizadores() {
        return utilizadorRepository.findAll();
    }

    // Método para procurar um utilizador por ID
    public Optional<Utilizador> buscarUtilizadorPorId(BigDecimal id) {
        return utilizadorRepository.findById(id);
    }

    // Método para atualizar um utilizador existente
    public Utilizador atualizarUtilizador(Utilizador utilizador) {
        if (utilizador.getId() == null) {
            throw new IllegalArgumentException("O ID do utilizador é obrigatório para atualização.");
        }
        return utilizadorRepository.save(utilizador);
    }

    // Método para remover um utilizador por ID
    public void removerUtilizador(BigDecimal id) {
        utilizadorRepository.deleteById(id);
    }
}
