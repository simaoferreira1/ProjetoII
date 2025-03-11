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

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(BigDecimal id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteById(BigDecimal id) {
        clienteRepository.deleteById(id);
    }

    public Cliente updateCliente(BigDecimal id, Cliente clienteDetails) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente not found"));
        cliente.setNome(clienteDetails.getNome());
        cliente.setEmail(clienteDetails.getEmail());
        cliente.setTelefone(clienteDetails.getTelefone());
        cliente.setEndereco(clienteDetails.getEndereco());
        return clienteRepository.save(cliente);
    }
}