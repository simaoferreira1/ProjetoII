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

    public Especialista salvarEspecialista(Especialista especialista) {
        return especialistaRepository.save(especialista);
    }

    public Optional<Especialista> buscarEspecialistaPorId(BigDecimal id) {
        return especialistaRepository.findById(id);
    }

    public List<Especialista> listarEspecialistas() {
        return especialistaRepository.findAll();
    }

    public Especialista atualizarEspecialista(Especialista especialista) {
        return especialistaRepository.save(especialista);
    }

    public void removerEspecialista(BigDecimal id) {
        especialistaRepository.deleteById(id);
    }
}

