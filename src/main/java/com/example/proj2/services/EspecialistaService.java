package com.example.proj2.services;

import com.example.proj2.models.Especialista;
import com.example.proj2.repository.EspecialistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialistaService {

    @Autowired
    private EspecialistaRepository especialistaRepository;

    // Método para salvar um novo especialista ou atualizar um existente
    public Especialista salvarEspecialista(Especialista especialista) {
        // Verifica se já existe um especialista com o mesmo e-mail
        if (especialista.getEmail() != null && especialistaRepository.existsByEmail(especialista.getEmail())) {
            throw new IllegalArgumentException("Já existe um especialista com esse e-mail.");
        }

        return especialistaRepository.save(especialista);
    }

    // Método para procurar um especialista através do seu ID
    public Optional<Especialista> buscarEspecialistaPorId(Long id) {
        return especialistaRepository.findById(id);
    }

    // Método para listar todos os especialistas
    public List<Especialista> listarEspecialistas() {
        return especialistaRepository.findAll();
    }

    // Método para atualizar as informações de um especialista
    public Especialista atualizarEspecialista(Especialista especialista) {
        return especialistaRepository.save(especialista);
    }

    // Método para remover um especialista através do seu ID
    public void removerEspecialista(Long id) {
        especialistaRepository.deleteById(id);
    }
}
