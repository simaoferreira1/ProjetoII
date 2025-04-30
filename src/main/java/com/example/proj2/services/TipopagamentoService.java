package com.example.proj2.services;

import com.example.proj2.models.Tipopagamento;
import com.example.proj2.repository.TipopagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipopagamentoService {

    private final TipopagamentoRepository tipopagamentoRepository;

    @Autowired
    public TipopagamentoService(TipopagamentoRepository tipopagamentoRepository) {
        this.tipopagamentoRepository = tipopagamentoRepository;
    }

    // Método para salvar um novo tipo de pagamento com regras de negócio
    public Tipopagamento salvarTipopagamento(Tipopagamento tipopagamento) {
        if (tipopagamento.getDescricao() == null || tipopagamento.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do tipo de pagamento é obrigatória.");
        }
        return tipopagamentoRepository.save(tipopagamento);
    }

    // Método para listar todos os tipos de pagamento
    public List<Tipopagamento> listarTipopagamentos() {
        return tipopagamentoRepository.findAll();
    }

    // Método para buscar um tipo de pagamento por ID
    public Optional<Tipopagamento> buscarTipopagamentoPorId(Integer id) {
        return tipopagamentoRepository.findById(id);
    }

    // Método para atualizar um tipo de pagamento existente
    public Tipopagamento atualizarTipopagamento(Tipopagamento tipopagamento) {
        if (tipopagamento.getId() == null) {
            throw new IllegalArgumentException("O ID do tipo de pagamento é obrigatório para atualização.");
        }
        return tipopagamentoRepository.save(tipopagamento);
    }

    // Método para remover um tipo de pagamento por ID
    public void removerTipopagamento(Integer id) {
        tipopagamentoRepository.deleteById(id);
    }
}
