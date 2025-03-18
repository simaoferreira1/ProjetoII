package com.example.proj2;

import com.example.proj2.services.ClienteService;
import com.example.proj2.services.EspecialistaService;
import com.example.proj2.services.GestordeprojetoService;
import com.example.proj2.services.LicencaService;
import com.example.proj2.models.Cliente;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Tipoespecialista;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Licenca;
import com.example.proj2.models.Projeto;
import com.example.proj2.repository.ProjetoRepository;
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

    @Autowired
    public Proj2Application(ClienteService clienteService, EspecialistaService especialistaService,
                            GestordeprojetoService gestordeprojetoService, LicencaService licencaService, ProjetoRepository projetoRepository)
    {
        this.clienteService = clienteService;
        this.especialistaService = especialistaService;
        this.gestordeprojetoService = gestordeprojetoService;
        this.licencaService = licencaService;
        this.projetoRepository = projetoRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Proj2Application.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            try {
                // ===================== CLIENTE =====================
                System.out.println("\n🔹 Operações com Cliente...");

                Cliente novoCliente = new Cliente();
                novoCliente.setId(BigDecimal.valueOf(1));
                novoCliente.setNome("Maria Oliveira");
                novoCliente.setEmail("maria.oliveira@exemplo.com");
                novoCliente.setTelefone(new BigDecimal("912345678"));
                novoCliente.setEndereco("Rua das Flores, 123");

                Cliente clienteSalvo = clienteService.salvarCliente(novoCliente);
                System.out.println("✅ Cliente inserido com sucesso!");

                Optional<Cliente> clienteOptional = clienteService.buscarClientePorId(clienteSalvo.getId());
                clienteOptional.ifPresent(cliente -> System.out.println("🔍 Cliente encontrado: " + cliente.getNome()));

                List<Cliente> clientes = clienteService.listarClientes();
                clientes.forEach(c -> System.out.println("📜 Cliente: " + c.getNome() + " - Email: " + c.getEmail()));

                if (clienteOptional.isPresent()) {
                    Cliente cliente = clienteService.buscarClientePorId(clienteSalvo.getId()).orElseThrow();
                    cliente.setEmail("maria.novoemail@exemplo.com");
                    clienteService.atualizarCliente(cliente);
                    System.out.println("✅ Cliente atualizado!");
                }

                clienteService.removerCliente(clienteSalvo.getId());
                System.out.println("🗑 Cliente removido!");

                // ===================== ESPECIALISTA =====================
/*                System.out.println("\n🔹 Operações com Especialista...");

// Criação do objeto Tipoespecialista (com id 6)
                Tipoespecialista tipoEspecialista = new Tipoespecialista();
                tipoEspecialista.setId(BigDecimal.valueOf(6));

// Criação do novo Especialista
                Especialista novoEspecialista = new Especialista();
// ATENÇÃO: Se o ID já existe, o Hibernate pode tratar a operação como update.
// Certifique-se de que este valor é apropriado para a sua estratégia de geração de IDs.
                novoEspecialista.setId(BigDecimal.valueOf(6));
                novoEspecialista.setNome("Dr. João Cardoso");
                novoEspecialista.setEmail("joao.cardoso@exemplo.com");
                novoEspecialista.setTelefone(new BigDecimal("912345678"));
                novoEspecialista.setTipoespecialistas(tipoEspecialista);

// Inserção do especialista
                Especialista especialistaSalvo = null;
                try {
                    especialistaSalvo = especialistaService.salvarEspecialista(novoEspecialista);
                    System.out.println("✅ Especialista inserido com sucesso!");
                } catch (ObjectOptimisticLockingFailureException ex) {
                    System.err.println("Erro ao salvar Especialista: " + ex.getMessage());
                    // Aqui você pode optar por re-tentar a inserção ou encerrar o processo
                }

                if (especialistaSalvo != null) {
                    // Busca e exibe o especialista inserido
                    Optional<Especialista> especialistaOptional = especialistaService.buscarEspecialistaPorId(especialistaSalvo.getId());
                    especialistaOptional.ifPresent(especialista ->
                            System.out.println("🔍 Especialista encontrado: " + especialista.getNome())
                    );

                    // Listagem de todos os especialistas
                    List<Especialista> especialistas = especialistaService.listarEspecialistas();
                    especialistas.forEach(e ->
                            System.out.println("📜 Especialista: " + e.getNome() + " - Email: " + e.getEmail())
                    );

                    // Atualização do email com re-tentativa em caso de lock otimista
                    final int MAX_RETRIES = 3;
                    int attempts = 0;
                    boolean updated = false;
                    while (!updated && attempts < MAX_RETRIES) {
                        try {
                            // Recarrega a entidade para obter o estado mais recente
                            Especialista especialistaParaAtualizar = especialistaService
                                    .buscarEspecialistaPorId(especialistaSalvo.getId())
                                    .orElseThrow(() -> new RuntimeException("Especialista não encontrado"));
                            especialistaParaAtualizar.setEmail("joao.novoemail@exemplo.com");
                            especialistaService.atualizarEspecialista(especialistaParaAtualizar);
                            System.out.println("✅ Especialista atualizado!");
                            updated = true;
                        } catch (ObjectOptimisticLockingFailureException ex) {
                            attempts++;
                            System.err.println("Tentativa " + attempts + " falhou na atualização devido a lock otimista: " + ex.getMessage());
                            // Aqui você pode esperar um tempo ou recarregar novamente a entidade antes de re-tentar
                        }
                    }
                    if (!updated) {
                        System.err.println("Falha ao atualizar o Especialista após " + MAX_RETRIES + " tentativas.");
                    }

                    // Remoção do especialista com tratamento de exceção
                    try {
                        especialistaService.removerEspecialista(especialistaSalvo.getId());
                        System.out.println("🗑 Especialista removido!");
                    } catch (Exception ex) {
                        System.err.println("Erro ao remover Especialista: " + ex.getMessage());
                    }
                }

*/

                // ===================== GESTOR DE PROJETO =====================
                System.out.println("\n🔹 Operações com Gestor de Projeto...");

                Gestordeprojeto novoGestor = new Gestordeprojeto();
                novoGestor.setId(BigDecimal.valueOf(3));
                novoGestor.setNome("Carlos Almeida");
                novoGestor.setEmail("carlos.almeida@exemplo.com");
                novoGestor.setTelefone(new BigDecimal("934567890"));

                Gestordeprojeto gestorSalvo = gestordeprojetoService.salvarGestor(novoGestor);
                System.out.println("✅ Gestor de projeto inserido com sucesso!");

                Optional<Gestordeprojeto> gestorOptional = gestordeprojetoService.buscarGestorPorId(gestorSalvo.getId());
                gestorOptional.ifPresent(gestor -> System.out.println("🔍 Gestor encontrado: " + gestor.getNome()));

                List<Gestordeprojeto> gestores = gestordeprojetoService.listarGestores();
                gestores.forEach(g -> System.out.println("📜 Gestor: " + g.getNome() + " - Email: " + g.getEmail()));

                if (gestorOptional.isPresent()) {
                    Gestordeprojeto gestor = gestordeprojetoService.buscarGestorPorId(gestorSalvo.getId()).orElseThrow();
                    gestor.setEmail("carlos.novoemail@exemplo.com");
                    gestordeprojetoService.atualizarGestor(gestor);
                    System.out.println("✅ Gestor atualizado!");
                }

                gestordeprojetoService.buscarGestorPorId(gestorSalvo.getId()).ifPresent(gestor -> {
                    gestordeprojetoService.removerGestor(gestor.getId());
                    System.out.println("🗑 Gestor de projeto removido!");
                });

                // ===================== LICENÇA =====================
                System.out.println("\n🔹 Operações com Licença...");

// Verifica se o Projeto com ID 2 existe no banco
                Optional<Projeto> projetoOptional = projetoRepository.findById(BigDecimal.valueOf(2));
                if (projetoOptional.isEmpty()) {
                    throw new RuntimeException("O projeto com ID 2 não existe no banco.");
                }
                Projeto projeto = projetoOptional.get();

// Cria uma nova Licença associada ao Projeto recuperado
                Licenca novaLicenca = new Licenca();
// NÃO defina manualmente o ID, pois estamos utilizando @MapsId;
// o ID da Licença será o mesmo do Projeto (nesse caso, 2)
                novaLicenca.setProjeto(projeto);
                novaLicenca.setDataemissao(LocalDate.of(2024, 3, 17));
                novaLicenca.setDatavalidade(LocalDate.of(2025, 3, 17));

// Salva a Licença
                Licenca licencaSalva = licencaService.salvarLicenca(novaLicenca);
                System.out.println("✅ Licença inserida com sucesso!");

// Busca a Licença salva e exibe seus dados
                Optional<Licenca> licencaOptional = licencaService.buscarLicencaPorId(licencaSalva.getId());
                licencaOptional.ifPresent(licenca ->
                        System.out.println("🔍 Licença encontrada: Emitida em " + licenca.getDataemissao() +
                                ", válida até " + licenca.getDatavalidade())
                );

// Atualiza a Licença: estende a validade para 17/03/2026
                licencaOptional.ifPresent(licenca -> {
                    licenca.setDatavalidade(LocalDate.of(2026, 3, 17));
                    licencaService.atualizarLicenca(licenca);
                    System.out.println("✅ Licença atualizada!");
                });

// Remove a Licença caso ela esteja presente
                licencaService.buscarLicencaPorId(licencaSalva.getId()).ifPresent(licenca -> {
                    licencaService.removerLicenca(licenca.getId());
                    System.out.println("🗑 Licença removida!");
                });



            } catch (Exception e) {
                System.err.println("❌ ERRO: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
