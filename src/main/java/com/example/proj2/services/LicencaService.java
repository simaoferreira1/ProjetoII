package com.example.proj2.services;

import com.example.proj2.models.Licenca;
import com.example.proj2.repository.LicencaRepository;
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

    @Autowired
    public LicencaService(LicencaRepository licencaRepository) {
        this.licencaRepository = licencaRepository;
    }

    // Método para salvar uma nova licença com regras de negócio
    public Licenca salvarLicenca(Licenca licenca) {
        // Validações de negócio
        if (licenca.getDataemissao() == null || licenca.getDatavalidade() == null) {
            throw new IllegalArgumentException("As datas de emissão e validade são obrigatórias.");
        }
        if (licenca.getDatavalidade().isBefore(licenca.getDataemissao())) {
            throw new IllegalArgumentException("A data de validade deve ser posterior à data de emissão.");
        }
        if (licenca.getProjeto() == null) {
            throw new IllegalArgumentException("O projeto associado à licença deve ser informado.");
        }
        return licencaRepository.save(licenca);
    }

    // Método para listar todas as licenças
    public List<Licenca> listarLicencas() {
        return licencaRepository.findAll();
    }

    // Método para buscar uma licença por ID
    public Optional<Licenca> buscarLicencaPorId(BigDecimal id) {
        return licencaRepository.findById(id);
    }

    // Método para atualizar uma licença existente
    public Licenca atualizarLicenca(Licenca licenca) {
        if (licenca.getId() == null) {
            throw new IllegalArgumentException("O ID da licença é obrigatório para atualização.");
        }
        // Outras regras de negócio para atualização podem ser aplicadas aqui
        return licencaRepository.save(licenca);
    }

    // Método para remover uma licença por ID
    public void removerLicenca(BigDecimal id) {
        licencaRepository.deleteById(id);
    }
}
