package com.example.proj2.services;

import com.example.proj2.models.Licenca;
import com.example.proj2.models.Projeto;
import com.example.proj2.repository.LicencaRepository;
import com.example.proj2.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LicencaService {

    private final LicencaRepository licencaRepository;
    private final ProjetoRepository projetoRepository; // Adicionamos o repositório do Projeto

    @Autowired
    public LicencaService(LicencaRepository licencaRepository, ProjetoRepository projetoRepository) {
        this.licencaRepository = licencaRepository;
        this.projetoRepository = projetoRepository;
    }

    // Método para salvar uma nova licença
    public Licenca salvarLicenca(Licenca licenca) {
        if (licenca.getId() == null) {
            throw new IllegalArgumentException("O ID da licença é obrigatório.");
        }

        // Verifica se já existe uma licença com o mesmo ID
        if (licencaRepository.existsById(licenca.getId())) {
            throw new RuntimeException("Já existe uma licença com ID " + licenca.getId());
        }

        // Verifica se o Projeto associado existe no banco de dados
        Optional<Projeto> projetoOptional = projetoRepository.findById(licenca.getProjeto().getId());
        if (projetoOptional.isEmpty()) {
            throw new RuntimeException("O projeto associado à licença não existe no banco.");
        }

        return licencaRepository.save(licenca);
    }

    // Método para listar todas as licenças
    public List<Licenca> listarLicencas() {
        return licencaRepository.findAll();
    }

    // Método para buscar uma licença por ID
    public Optional<Licenca> buscarLicencaPorId(Integer id) {
        return licencaRepository.findById(id);
    }

    // Método para atualizar uma licença existente
    public Licenca atualizarLicenca(Licenca licenca) {
        if (licenca.getId() == null) {
            throw new IllegalArgumentException("O ID da licença é obrigatório para atualização.");
        }

        return licencaRepository.save(licenca);
    }

    // Método para remover uma licença por ID
    public void removerLicenca(Integer id) {
        licencaRepository.deleteById(id);
    }
}
