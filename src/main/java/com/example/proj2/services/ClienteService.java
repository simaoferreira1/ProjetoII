package com.example.proj2.services;

import com.example.proj2.models.Cliente;
import com.example.proj2.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // Construtor da classe, injetando a dependência do repositório ClienteRepository
    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Método para salvar um novo cliente ou atualizar um existente
    public Cliente salvarCliente(Cliente cliente) throws IllegalArgumentException {
        // Validação: nome do cliente é obrigatório
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        // Validação: e-mail do cliente deve ser único
        if (cliente.getEmail() != null && clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente com esse e-mail.");
        }

        return clienteRepository.save(cliente);
    }

    // Método para buscar um cliente pelo seu ID
    public Optional<Cliente> buscarClientePorId(Integer id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("O ID do cliente não pode ser nulo.");
        }
        return clienteRepository.findById(id);
    }

    // Método para listar todos os clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Método para atualizar as informações de um cliente
    public Cliente atualizarCliente(Cliente cliente) throws IllegalArgumentException {
        if (cliente.getId() == null) {
            throw new IllegalArgumentException("O ID do cliente é obrigatório para a atualização.");
        }
        return clienteRepository.save(cliente);
    }

    // Método para remover um cliente através do seu ID
    public void removerCliente(Integer id) throws IllegalArgumentException {
        if (id == null || !clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("O cliente com o ID fornecido não existe.");
        }
        clienteRepository.deleteById(id);
    }
}
