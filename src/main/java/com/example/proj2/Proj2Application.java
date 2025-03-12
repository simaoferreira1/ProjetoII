package com.example.proj2;

import com.example.proj2.services.ClienteService;
import com.example.proj2.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Proj2Application {

    private final ClienteService clienteService;

    @Autowired
    public Proj2Application(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Proj2Application.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            try {
                // Criar um novo cliente
                Cliente novoCliente = new Cliente();
                novoCliente.setId(BigDecimal.valueOf(1)); // Definir ID manualmente ou usar estratégia no banco
                novoCliente.setNome("Maria Oliveira");
                novoCliente.setEmail("maria.oliveira@exemplo.com");
                novoCliente.setTelefone(new BigDecimal("912345678"));
                novoCliente.setEndereco("Rua das Flores, 123");

                Cliente clienteSalvo = clienteService.salvarCliente(novoCliente);
                System.out.println("✅ Cliente inserido com sucesso!");

                // Buscar um cliente pelo ID
                Optional<Cliente> clienteOptional = clienteService.buscarClientePorId(clienteSalvo.getId());
                clienteOptional.ifPresent(cliente -> System.out.println("🔍 Cliente encontrado: " + cliente.getNome()));

                // Listar todos os clientes
                List<Cliente> clientes = clienteService.listarClientes();
                clientes.forEach(c -> System.out.println("📜 Cliente: " + c.getNome() + " - Email: " + c.getEmail()));

                // Atualizar cliente
                if (clienteOptional.isPresent()) {
                    Cliente cliente = clienteOptional.get();
                    cliente.setEmail("maria.novoemail@exemplo.com");
                    clienteService.atualizarCliente(cliente);
                    System.out.println("✅ Cliente atualizado!");
                }

                // Remover cliente
                clienteService.removerCliente(clienteSalvo.getId());
                System.out.println("🗑 Cliente removido!");

            } catch (Exception e) {
                System.err.println("❌ ERRO: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
