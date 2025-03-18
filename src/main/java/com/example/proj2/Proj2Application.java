package com.example.proj2;

import com.example.proj2.services.*;
import com.example.proj2.models.Cliente;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Tipoespecialista;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Tipopagamento;
import com.example.proj2.models.Licenca;
import com.example.proj2.models.Projeto;
import com.example.proj2.models.Utilizador;
import com.example.proj2.models.Tipoutilizador;
import com.example.proj2.repository.ProjetoRepository;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Proj2Application {

    private final ClienteService clienteService;
    private final EspecialistaService especialistaService;
    private final GestordeprojetoService gestordeprojetoService;
    private final LicencaService licencaService;
    private final ProjetoRepository projetoRepository;
    private final UtilizadorService utilizadorService;
    private final ProjetoService projetoService;
    private final TipoespecialistaService tipoespecialistaService;
    private final TipopagamentoService tipopagamentoService;

    @Autowired
    public Proj2Application(ClienteService clienteService, EspecialistaService especialistaService,
                            GestordeprojetoService gestordeprojetoService, LicencaService licencaService,
                            ProjetoRepository projetoRepository, UtilizadorService utilizadorService,
                            ProjetoService projetoService, TipoespecialistaService tipoespecialistaService,
                            TipopagamentoService tipopagamentoService)
    {
        this.clienteService = clienteService;
        this.especialistaService = especialistaService;
        this.gestordeprojetoService = gestordeprojetoService;
        this.licencaService = licencaService;
        this.projetoRepository = projetoRepository;
        this.utilizadorService = utilizadorService;
        this.projetoService = projetoService;
        this.tipoespecialistaService = tipoespecialistaService;
        this.tipopagamentoService = tipopagamentoService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Proj2Application.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            try {
                // ===================== CLIENTE =====================
                System.out.println("\n=================================================================================================================================================");
                System.out.println("===== Operações com o Cliente =====");

                Cliente novoCliente = new Cliente();
                novoCliente.setId(BigDecimal.valueOf(1));
                novoCliente.setNome("Maria Oliveira");
                novoCliente.setEmail("maria.oliveira@exemplo.com");
                novoCliente.setTelefone(new BigDecimal("912345678"));
                novoCliente.setEndereco("Rua das Flores, 123");

                System.out.println("\n||   Inserir novo cliente   ||");
                Cliente clienteSalvo = clienteService.salvarCliente(novoCliente);
                System.out.println("Cliente inserido com sucesso!");


                System.out.println("\n||   Procurar cliente por id   ||");
                Optional<Cliente> clienteOptional = clienteService.buscarClientePorId(clienteSalvo.getId());
                clienteOptional.ifPresent(cliente -> System.out.println("||Cliente encontrado: " + cliente.getNome()));

                System.out.println("\n||   Listar clientes   ||");
                List<Cliente> clientes = clienteService.listarClientes();
                clientes.forEach(c -> System.out.println("Cliente: " + c.getNome() + " - Email: " + c.getEmail()));

                System.out.println("\n||   Atualizar clientes   ||");
                if (clienteOptional.isPresent()) {
                    Cliente cliente = clienteService.buscarClientePorId(clienteSalvo.getId()).orElseThrow();
                    cliente.setEmail("maria.novoemail@exemplo.com");
                    clienteService.atualizarCliente(cliente);
                    System.out.println("Cliente atualizado!");
                }

                System.out.println("\n||   Remover cliente   ||");
                clienteService.removerCliente(clienteSalvo.getId());
                System.out.println("🗑 Cliente removido!");


                System.out.println("\n=================================================================================================================================================");
                // ===================== GESTOR DE PROJETO =====================
                System.out.println("===== Operações com Gestor de Projeto =====\n");

                System.out.println("||   Inserir novo gestor de projeto   ||");
                Gestordeprojeto novoGestor = new Gestordeprojeto();
                novoGestor.setId(BigDecimal.valueOf(4));
                novoGestor.setNome("Tomás Silva");
                novoGestor.setEmail("tomas.silva@exemplo.com");
                novoGestor.setTelefone(new BigDecimal("965739572"));

// Salva o Gestor (se já existir, o serviço retorna o existente)
                Gestordeprojeto gestorSalvo = gestordeprojetoService.salvarGestor(novoGestor);
                System.out.println(" Gestor de Projeto já existe: " + gestorSalvo.getNome());

                System.out.println("\n||   Procurar gestor de projeto por ID   ||");
                Optional<Gestordeprojeto> gestorOptional = gestordeprojetoService.buscarGestorPorId(gestorSalvo.getId());
                gestorOptional.ifPresent(gestor -> System.out.println(" Gestor encontrado: " + gestor.getNome()));

                System.out.println("\n||   Listar gestores de projeto   ||");
                List<Gestordeprojeto> gestores = gestordeprojetoService.listarGestores();
                gestores.forEach(g -> System.out.println(" Gestor: " + g.getNome() + " - Email: " + g.getEmail()));

                System.out.println("\n||   Atualizar gestor de projeto   ||");
                if (gestorOptional.isPresent()) {
                    Gestordeprojeto gestor = gestordeprojetoService.buscarGestorPorId(gestorSalvo.getId()).orElseThrow();
                    gestor.setEmail("carlos.novoemail@exemplo.com");
                    gestordeprojetoService.atualizarGestor(gestor);
                    System.out.println("Gestor atualizado!");
                }

                System.out.println("\n||   Remover gestor de projeto   ||");
                gestordeprojetoService.buscarGestorPorId(gestorSalvo.getId()).ifPresent(gestor -> {
                    gestordeprojetoService.removerGestor(gestor.getId());
                    System.out.println("Gestor de projeto removido!");
                });

                System.out.println("\n=================================================================================================================================================");
                // ===================== UTILIZADOR =====================
                System.out.println("===== Operações com Utilizador =====");

                // Criação de um novo Utilizador
                Utilizador novoUtilizador = new Utilizador();
                novoUtilizador.setId(BigDecimal.valueOf(1));
                novoUtilizador.setNome("João Silva");
                novoUtilizador.setUser("joaosilva");
                novoUtilizador.setPassword("123456");

                // Criação e associação do Tipo de Utilizador
                Tipoutilizador tipoutilizador = new Tipoutilizador();
                tipoutilizador.setId(BigDecimal.valueOf(1));
                novoUtilizador.setIdtipoutilizador(tipoutilizador);

                System.out.println("\n||   Inserir novo utilizador   ||");
                Utilizador utilizadorSalvo = utilizadorService.salvarUtilizador(novoUtilizador);
                System.out.println("Utilizador inserido com sucesso!");

                System.out.println("\n||   Procurar utilizador por ID   ||");
                Optional<Utilizador> utilizadorOptional = utilizadorService.buscarUtilizadorPorId(utilizadorSalvo.getId());
                utilizadorOptional.ifPresent(utilizador ->
                        System.out.println("Utilizador encontrado: " + utilizador.getNome())
                );

                System.out.println("\n||   Listar todos os utilizadores   ||");
                List<Utilizador> utilizadores = utilizadorService.listarUtilizadores();
                utilizadores.forEach(u ->
                        System.out.println("Utilizador: " + u.getNome() + " - Username: " + u.getUser())
                );

                System.out.println("\n||   Atualizar utilizador   ||");
                if (utilizadorOptional.isPresent()) {
                    Utilizador utilizador = utilizadorService.buscarUtilizadorPorId(utilizadorSalvo.getId()).orElseThrow();
                    utilizador.setPassword("novasenha123");
                    utilizadorService.atualizarUtilizador(utilizador);
                    System.out.println("Utilizador atualizado!");
                }

                System.out.println("\n||   Remover utilizador   ||");
                utilizadorService.removerUtilizador(utilizadorSalvo.getId());
                System.out.println("Utilizador removido!");

                System.out.println("\n=================================================================================================================================================");
                // ===================== TIPO DE ESPECIALISTA =====================
                System.out.println("===== Operações com Tipo de Especialista =====\n");

// Criação de um novo Tipo de Especialista
                Tipoespecialista novoTipo = new Tipoespecialista();
                novoTipo.setId(BigDecimal.valueOf(6)); // Defina o ID manualmente se essa for sua estratégia (ou remova se for gerado automaticamente)
                novoTipo.setDescricao("Eletrecista");

                System.out.println("\n||   Adicionar novo tipo de especialista   ||");
                Tipoespecialista tipoSalvo = tipoespecialistaService.salvarTipoespecialista(novoTipo);
                System.out.println("Tipo de Especialista inserido com sucesso!");

                System.out.println("\n||   Procurar tipo de especialista   ||");
                Optional<Tipoespecialista> tipoOptional = tipoespecialistaService.buscarTipoespecialistaPorId(tipoSalvo.getId());
                tipoOptional.ifPresent(tipo ->
                        System.out.println("Tipo de Especialista encontrado: " + tipo.getDescricao())
                );

                System.out.println("\n||   Listar tipo de especialistas   ||");
                List<Tipoespecialista> tipos = tipoespecialistaService.listarTipoespecialistas();
                tipos.forEach(t ->
                        System.out.println("Tipo de Especialista: " + t.getDescricao())
                );

                System.out.println("\n||   Atualizar tipo de especialistas   ||");
                if (tipoOptional.isPresent()) {
                    Tipoespecialista tipoParaAtualizar = tipoespecialistaService.buscarTipoespecialistaPorId(tipoSalvo.getId())
                            .orElseThrow(() -> new RuntimeException("Tipo de Especialista não encontrado."));
                    tipoParaAtualizar.setDescricao("Neurologista");
                    tipoespecialistaService.atualizarTipoespecialista(tipoParaAtualizar);
                    System.out.println("Tipo de Especialista atualizado!");
                }

                System.out.println("\n||   Remover tipo de especialistas   ||");
                tipoespecialistaService.removerTipoespecialista(tipoSalvo.getId());
                System.out.println("Tipo de Especialista removido!");

                System.out.println("\n=================================================================================================================================================");
                // ===================== TIPO DE PAGAMENTO =====================
                System.out.println("===== Operações com Tipo de Pagamento =====\n");


                Tipopagamento novoTipoPagamento = new Tipopagamento();
                novoTipoPagamento.setId(BigDecimal.valueOf(1)); // Defina o ID manualmente, se essa for sua estratégia
                novoTipoPagamento.setDescricao("Cartão de Crédito");

                System.out.println("\n||   Criar novo tipo de pagamento   ||");
                Tipopagamento tipoPagamentoSalvo = tipopagamentoService.salvarTipopagamento(novoTipoPagamento);
                System.out.println("Tipo de Pagamento inserido com sucesso!");

                System.out.println("\n||   Procurar tipo de pagamento   ||");
                Optional<Tipopagamento> tipoPagamentoOptional = tipopagamentoService.buscarTipopagamentoPorId(tipoPagamentoSalvo.getId());
                tipoPagamentoOptional.ifPresent(tipo ->
                        System.out.println("Tipo de Pagamento encontrado: " + tipo.getDescricao())
                );

                System.out.println("\n||   Listar tipos de pagamento   ||");
                List<Tipopagamento> tiposPagamento = tipopagamentoService.listarTipopagamentos();
                tiposPagamento.forEach(tipo ->
                        System.out.println("Tipo de Pagamento: " + tipo.getDescricao())
                );

                System.out.println("\n||   Atualizar tipo de pagamento   ||");
                if (tipoPagamentoOptional.isPresent()) {
                    Tipopagamento tipoParaAtualizar = tipopagamentoService
                            .buscarTipopagamentoPorId(tipoPagamentoSalvo.getId())
                            .orElseThrow(() -> new RuntimeException("Tipo de Pagamento não encontrado"));
                    tipoParaAtualizar.setDescricao("Cartão débito");
                    tipopagamentoService.atualizarTipopagamento(tipoParaAtualizar);
                    System.out.println("Tipo de Pagamento atualizado!");
                }

                System.out.println("\n||   Remover tipo de pagamento   ||");
                tipopagamentoService.removerTipopagamento(tipoPagamentoSalvo.getId());
                System.out.println("Tipo de Pagamento removido!");
                System.out.println("\n=================================================================================================================================================\n\n");


            } catch (Exception e) {
                System.err.println("ERRO: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
