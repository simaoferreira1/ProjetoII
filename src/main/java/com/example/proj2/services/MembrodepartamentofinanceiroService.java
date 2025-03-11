package com.example.proj2.services;

import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.repository.MembrodepartamentofinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MembrodepartamentofinanceiroService {

    private final MembrodepartamentofinanceiroRepository membroRepository;

    @Autowired
    public MembrodepartamentofinanceiroService(MembrodepartamentofinanceiroRepository membroRepository) {
        this.membroRepository = membroRepository;
    }

    // Método para salvar um novo membro com regras de negócio
    public Membrodepartamentofinanceiro salvarMembro(Membrodepartamentofinanceiro membro) {
        if (membro.getNome() == null || membro.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do membro é obrigatório.");
        }
        if (membro.getEmail() == null || membro.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O email do membro é obrigatório.");
        }
        // Outras regras de negócio podem ser adicionadas aqui

        return membroRepository.save(membro);
    }

    // Método para listar todos os membros
    public List<Membrodepartamentofinanceiro> listarMembros() {
        return membroRepository.findAll();
    }

    // Método para buscar um membro por ID
    public Optional<Membrodepartamentofinanceiro> buscarMembroPorId(BigDecimal id) {
        return membroRepository.findById(id);
    }

    // Método para atualizar um membro
    public Membrodepartamentofinanceiro atualizarMembro(Membrodepartamentofinanceiro membro) {
        if (membro.getId() == null) {
            throw new IllegalArgumentException("O ID do membro é obrigatório para atualização.");
        }
        // Regras de negócio para atualização podem ser aplicadas aqui
        return membroRepository.save(membro);
    }

    // Método para remover um membro por ID
    public void removerMembro(BigDecimal id) {
        membroRepository.deleteById(id);
    }
}
