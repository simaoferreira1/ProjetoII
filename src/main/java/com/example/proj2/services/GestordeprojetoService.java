package com.example.proj2.services;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.repository.GestordeprojetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestordeprojetoService {

    private final GestordeprojetoRepository gestordeprojetoRepository = new GestordeprojetoRepository();

    public Gestordeprojeto salvarOuAtualizar(Gestordeprojeto gestor) {
        if (gestor.getId() != null && gestordeprojetoRepository.existsById(gestor.getId())) {
            gestordeprojetoRepository.save(gestor);
            return gestordeprojetoRepository.findById(gestor.getId()); // devolve da base de dados
        } else {
            gestordeprojetoRepository.save(gestor);
            return gestor;
        }
    }

    public Gestordeprojeto findById(Integer id) {
        return gestordeprojetoRepository.findById(id);
    }

    public List<Gestordeprojeto> findAll() {
        return gestordeprojetoRepository.findAll();
    }

    public void deleteById(Integer id) {
        gestordeprojetoRepository.deleteById(id);
    }
}
