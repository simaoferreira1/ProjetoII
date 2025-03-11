package com.example.proj2.services;

import com.example.proj2.models.Especialista;
import com.example.proj2.repository.EspecialistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EspecialistaService {

    private final EspecialistaRepository especialistaRepository;

    @Autowired
    public EspecialistaService(EspecialistaRepository especialistaRepository) {
        this.especialistaRepository = especialistaRepository;
    }

    // Método para salvar um especialista com regras de negócio
    public Especialista salvarEspecialista(Especialista especialista) {
        // Exemplo de validação: email não pode ser nulo ou vazio
        if (especialista.getEmail() == null || especialista.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O email é obrigatório.");
        }
        // Outras regras de negócio podem ser adicionadas aqui
        return especialistaRepository.save(especialista);
    }

    // Método para listar todos os especialistas
    public List<Especialista> listarEspecialistas() {
        return especialistaRepository.findAll();
    }

    // Método para buscar um especialista por ID
    public Optional<Especialista> buscarPorId(BigDecimal id) {
        return especialistaRepository.findById(id);
    }

    // Método para atualizar um especialista
    public Especialista atualizarEspecialista(Especialista especialista) {
        if (especialista.getId() == null) {
            throw new IllegalArgumentException("ID do especialista é obrigatório para atualização.");
        }
        // Regras de negócio adicionais para atualização podem ser aplicadas aqui
        return especialistaRepository.save(especialista);
    }

    // Método para remover um especialista por ID
    public void removerEspecialista(BigDecimal id) {
        especialistaRepository.deleteById(id);
    }
}
