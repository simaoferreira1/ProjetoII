package com.example.proj2.services;

import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.repository.MembrodepartamentofinanceiroRepository;

import java.util.List;

public class MembrodepartamentofinanceiroService {

    private final MembrodepartamentofinanceiroRepository membroRepository = new MembrodepartamentofinanceiroRepository();

    public Membrodepartamentofinanceiro salvarOuAtualizar(Membrodepartamentofinanceiro membro) {
        membroRepository.save(membro);
        return membro;
    }

    public List<Membrodepartamentofinanceiro> findAll() {
        return membroRepository.findAll();
    }

    public Membrodepartamentofinanceiro findById(Integer id) {
        return membroRepository.findById(id);
    }

    public void deleteById(Integer id) {
        membroRepository.deleteById(id);
    }
}
