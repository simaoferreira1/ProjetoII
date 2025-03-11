package com.example.proj2.services;

import com.example.proj2.models.Cliente;
import com.example.proj2.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Método para salvar um novo cliente com regras de negócio
    public Cliente salvarCliente(Cliente cliente) {
        // Validação: nome e email devem ser informados
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O email do cliente é obrigatório.");
        }
        // Outras regras de negócio podem ser aplicadas aqui

        return clienteRepository.save(cliente);
    }

    // Método para listar todos os clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Método para buscar um cliente por ID
    public Optional<Cliente> buscarClientePorId(BigDecimal id) {
        return clienteRepository.findById(id);
    }

    // Método para atualizar os dados de um cliente
    public Cliente atualizarCliente(Cliente cliente) {
        if (cliente.getId() == null) {
            throw new IllegalArgumentException("ID do cliente é obrigatório para atualização.");
        }
        // Aqui, você pode aplicar outras regras de negócio necessárias para a atualização
        return clienteRepository.save(cliente);
    }

    // Método para remover um cliente por ID
    public void removerCliente(BigDecimal id) {
        clienteRepository.deleteById(id);
    }
}
