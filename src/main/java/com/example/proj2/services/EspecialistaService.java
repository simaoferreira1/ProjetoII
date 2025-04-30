package com.example.proj2.services;

import com.example.proj2.models.Especialista;
import com.example.proj2.repository.EspecialistaRepository;

import java.math.BigDecimal;
import java.util.List;

public class EspecialistaService {

    private final EspecialistaRepository especialistaRepository = new EspecialistaRepository();

    public Especialista salvarOuAtualizar(Especialista especialista) {
        if (especialista.getEmail() != null && especialistaRepository.existsByEmail(especialista.getEmail())) {
            especialistaRepository.save(especialista);
            return especialistaRepository.findById(especialista.getId());
        } else {
            especialistaRepository.save(especialista);
            return especialista;
        }
    }

    public Especialista findById(Integer id) {
        return especialistaRepository.findById(id);
    }

    public List<Especialista> findAll() {
        return especialistaRepository.findAll();
    }

    public void deleteById(Integer id) {
        especialistaRepository.deleteById(id);
    }
}
