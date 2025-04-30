package com.example.proj2.services;

import com.example.proj2.models.Tipoespecialista;
import com.example.proj2.repository.TipoespecialistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoespecialistaService {

    private final TipoespecialistaRepository tipoespecialistaRepository;

    @Autowired
    public TipoespecialistaService(TipoespecialistaRepository tipoespecialistaRepository) {
        this.tipoespecialistaRepository = tipoespecialistaRepository;
    }

    // Método para salvar um novo tipo de especialista com regras de negócio
    public Tipoespecialista salvarTipoespecialista(Tipoespecialista tipoespecialista) {
        if (tipoespecialista.getDescricao() == null || tipoespecialista.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição é obrigatória.");
        }
        return tipoespecialistaRepository.save(tipoespecialista);
    }

    // Método para listar todos os tipos de especialistas
    public List<Tipoespecialista> listarTipoespecialistas() {
        return tipoespecialistaRepository.findAll();
    }

    // Método para buscar um tipo de especialista por ID
    public Optional<Tipoespecialista> buscarTipoespecialistaPorId(Integer id) {
        return tipoespecialistaRepository.findById(id);
    }

    // Método para atualizar um tipo de especialista existente
    public Tipoespecialista atualizarTipoespecialista(Tipoespecialista tipoespecialista) {
        if (tipoespecialista.getId() == null) {
            throw new IllegalArgumentException("O ID do tipo de especialista é obrigatório para atualização.");
        }
        return tipoespecialistaRepository.save(tipoespecialista);
    }

    // Método para remover um tipo de especialista por ID
    public void removerTipoespecialista(Integer id) {
        tipoespecialistaRepository.deleteById(id);
    }
}
