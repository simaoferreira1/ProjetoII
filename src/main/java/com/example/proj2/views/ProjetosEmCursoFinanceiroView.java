package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Cliente;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.models.Orcamentoprojeto;
import com.example.proj2.models.Pagamento;
import com.example.proj2.models.Projeto;
import com.example.proj2.repository.PagamentoRepository;
import com.example.proj2.services.OrcamentoprojetoService;
import com.example.proj2.services.ProjetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProjetosEmCursoFinanceiroView {

    private final Stage stage;
    private final Membrodepartamentofinanceiro financeiro;

    public ProjetosEmCursoFinanceiroView(Stage stage, Membrodepartamentofinanceiro financeiro) {
        this.stage = stage;
        this.financeiro = financeiro;
    }

    public void show() {
        BorderPane root = new BorderPane();

        // === MENU LATERAL ===
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Financeiro");
        nome.setStyle("-fx-font-size: 16px;");

        // Estilo para os botões com texto centralizado
        String estiloBtn = "-fx-background-color: #ffffff; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-pref-width: 180px; " +
                "-fx-pref-height: 60px; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-border-color: #cccccc; " +
                "-fx-border-width: 1px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2); " +
                "-fx-alignment: center;"; // Centralizar o texto dentro do botão

        // Estilo para o efeito de hover
        String estiloHover = "-fx-background-color: #e0e0e0; " +
                "-fx-scale-x: 1.02; " +
                "-fx-scale-y: 1.02; ";

        Button btnPedidosOrcamento = new Button("💰 Pedidos de Orçamento");
        Button btnProjetosCurso = new Button("🗂 Projetos em curso");
        Button btnSair = new Button("↩ Sair");

        for (Button btn : new Button[]{btnPedidosOrcamento, btnProjetosCurso, btnSair}) {
            btn.setStyle(estiloBtn);
            btn.setWrapText(true);
            // Adicionar efeito de hover
            btn.setOnMouseEntered(e -> btn.setStyle(estiloBtn + estiloHover));
            btn.setOnMouseExited(e -> btn.setStyle(estiloBtn));
        }

        btnPedidosOrcamento.setOnAction(event -> new PedidosOrcamentoView(stage, financeiro).show());
        btnSair.setOnAction(event -> stage.close());

        // Adicionar espaçador para empurrar o botão "Sair" para o final
        Region spacerMenu = new Region();
        VBox.setVgrow(spacerMenu, Priority.ALWAYS);

        menu.getChildren().addAll(nome, btnPedidosOrcamento, btnProjetosCurso, spacerMenu, btnSair);

        // === CONTEÚDO CENTRAL ===
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 20, 20, 20));

        Label titulo = new Label("Projetos em curso");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        content.getChildren().add(titulo);

        VBox listaProjetos = new VBox(10);
        ProjetoService projetoService = SpringContext.getBean(ProjetoService.class);
        OrcamentoprojetoService orcamentoService = SpringContext.getBean(OrcamentoprojetoService.class);
        PagamentoRepository pagamentoRepository = SpringContext.getBean(PagamentoRepository.class);
        List<Projeto> projetos = projetoService.listarProjetos().stream()
                .filter(p -> "em curso".equalsIgnoreCase(p.getEstado()))
                .toList();

        // Buscar todos os orçamentos para associar aos projetos
        List<Orcamentoprojeto> orcamentos = orcamentoService.listarOrcamentoprojetos();

        for (Projeto projeto : projetos) {
            HBox card = new HBox(15);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px;");
            card.setAlignment(Pos.CENTER_LEFT);

            VBox info = new VBox(5);
            Label nomeProjeto = new Label(projeto.getNome());
            nomeProjeto.setStyle("-fx-font-weight: bold;");
            Label descricao = new Label(projeto.getDescricao());

            info.getChildren().addAll(nomeProjeto, descricao);

            // Buscar o orçamento do projeto
            Optional<Orcamentoprojeto> orcamentoOpt = orcamentos.stream()
                    .filter(o -> o.getProjeto() != null && o.getProjeto().getId().equals(projeto.getId()))
                    .findFirst();
            BigDecimal orcamentoAtual = orcamentoOpt.map(Orcamentoprojeto::getValortotal).orElse(BigDecimal.ZERO);

            // Botões do card
            Button btnAbrir = new Button("Abrir");
            btnAbrir.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black;");

            Button btnAlterarOrcamento = new Button("Alterar Orçamento");
            btnAlterarOrcamento.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black;");

            Button btnGerarRelatorio = new Button("Gerar Relatório");
            btnGerarRelatorio.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black;");

            // Ação do botão "Abrir" (Consultar Entradas/Saídas e Verificar Pagamentos)
            btnAbrir.setOnAction(event -> {
                // Buscar o cliente associado ao projeto
                Cliente cliente = projeto.getIdcliente();
                BigDecimal entradasFinanceiras = BigDecimal.ZERO;
                if (cliente != null) {
                    // Buscar os pagamentos associados ao cliente
                    List<Pagamento> pagamentos = pagamentoRepository.findByCliente(cliente);
                    // Calcular Entradas Financeiras (baseado nos pagamentos realizados)
                    entradasFinanceiras = pagamentos.stream()
                            .map(Pagamento::getValor)
                            .filter(valor -> valor != null) // Evitar NullPointerException
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }

                // Calcular Saídas Financeiras (baseado no orçamento aprovado)
                BigDecimal saidasFinanceiras = orcamentoAtual;

                // Calcular Pagamentos Pendentes
                BigDecimal pagamentosPendentes = entradasFinanceiras.subtract(saidasFinanceiras);

                // Buscar o gestor encarregue do projeto usando o relacionamento direto
                String nomeGestor = "Desconhecido";
                Gestordeprojeto gestor = projeto.getGestordeprojeto();
                if (gestor != null) {
                    nomeGestor = gestor.getNome() != null ? gestor.getNome() : "Desconhecido";
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Detalhes do Projeto");
                alert.setHeaderText(projeto.getNome());
                alert.setContentText("Descrição: " + projeto.getDescricao() + "\n" +
                        "Data de Início: " + projeto.getDatainicio() + "\n" +
                        "Data de Fim Prevista: " + projeto.getDatafimprevista() + "\n" +
                        "Localização: " + projeto.getLocalizacao() + "\n" +
                        "Estado: " + projeto.getEstado() + "\n" +
                        "Entradas Financeiras: €" + entradasFinanceiras + "\n" +
                        "Saídas Financeiras: €" + saidasFinanceiras + "\n" +
                        "Pagamentos Pendentes: €" + pagamentosPendentes + "\n" +
                        "Pagamentos Realizados: €" + entradasFinanceiras + "\n" +
                        "Gestor Encarregue: " + nomeGestor + "\n" +
                        "Orçamento: €" + orcamentoAtual);
                alert.showAndWait();
            });

            // Ação do botão "Alterar Orçamento"
            btnAlterarOrcamento.setOnAction(event -> {
                TextInputDialog dialog = new TextInputDialog(orcamentoAtual.toString());
                dialog.setTitle("Alterar Orçamento");
                dialog.setHeaderText("Digite o novo orçamento para o projeto \"" + projeto.getNome() + "\":");
                dialog.setContentText("Novo Orçamento (€):");

                dialog.showAndWait().ifPresent(novoOrcamento -> {
                    try {
                        BigDecimal novoValor = new BigDecimal(novoOrcamento);

                        // Criar ou atualizar o orçamento
                        Orcamentoprojeto orcamento;
                        if (orcamentoOpt.isPresent()) {
                            // Atualizar orçamento existente
                            orcamento = orcamentoOpt.get();
                            orcamento.setValortotal(novoValor);
                            orcamento.setDataaprovacao(LocalDate.now());
                            orcamento.setEstado("aprovado");
                            orcamentoService.atualizarOrcamentoprojeto(orcamento);
                        } else {
                            // Criar novo orçamento
                            orcamento = new Orcamentoprojeto();
                            orcamento.setId(BigDecimal.valueOf(orcamentoService.listarOrcamentoprojetos().size() + 1));
                            orcamento.setValortotal(novoValor);
                            orcamento.setDataaprovacao(LocalDate.now());
                            orcamento.setEstado("aprovado");
                            orcamento.setProjeto(projeto);
                            orcamentoService.salvarOrcamentoprojeto(orcamento);
                        }

                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Sucesso");
                        success.setHeaderText(null);
                        success.setContentText("Orçamento atualizado com sucesso!");
                        success.showAndWait();

                        show(); // Recarrega a página para atualizar a lista
                    } catch (NumberFormatException e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erro");
                        error.setHeaderText(null);
                        error.setContentText("Por favor, insira um valor numérico válido.");
                        error.showAndWait();
                    } catch (IllegalArgumentException e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erro");
                        error.setHeaderText(null);
                        error.setContentText(e.getMessage());
                        error.showAndWait();
                    }
                });
            });

            // Ação do botão "Gerar Relatório" (Criar Relatório Financeiro de Progresso)
            btnGerarRelatorio.setOnAction(event -> {
                // Buscar o cliente associado ao projeto
                Cliente cliente = projeto.getIdcliente();
                BigDecimal entradasFinanceiras = BigDecimal.ZERO;
                if (cliente != null) {
                    // Buscar os pagamentos associados ao cliente
                    List<Pagamento> pagamentos = pagamentoRepository.findByCliente(cliente);
                    // Calcular Entradas Financeiras (baseado nos pagamentos realizados)
                    entradasFinanceiras = pagamentos.stream()
                            .map(Pagamento::getValor)
                            .filter(valor -> valor != null) // Evitar NullPointerException
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }

                // Calcular Saídas Financeiras (baseado no orçamento aprovado)
                BigDecimal saidasFinanceiras = orcamentoAtual;

                // Percentual do orçamento utilizado (se orçamento > 0)
                BigDecimal percentualOrcamentoUtilizado = orcamentoAtual.compareTo(BigDecimal.ZERO) > 0 ?
                        saidasFinanceiras.divide(orcamentoAtual, 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)) :
                        BigDecimal.ZERO;

                // Progresso fictício (pode ser ajustado com dados reais)
                BigDecimal progresso = new BigDecimal("40.00");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Relatório Financeiro de Progresso");
                alert.setHeaderText("Relatório para o projeto \"" + projeto.getNome() + "\"");
                alert.setContentText("Entradas Financeiras: €" + entradasFinanceiras + "\n" +
                        "Saídas Financeiras: €" + saidasFinanceiras + "\n" +
                        "Saldo (Entradas - Saídas): €" + entradasFinanceiras.subtract(saidasFinanceiras) + "\n" +
                        "Orçamento Total: €" + orcamentoAtual + "\n" +
                        "Percentual do Orçamento Utilizado: " + percentualOrcamentoUtilizado + "%\n" +
                        "Progresso do Projeto: " + progresso + "%");
                alert.showAndWait();
            });

            HBox botoes = new HBox(10, btnAbrir, btnAlterarOrcamento, btnGerarRelatorio);
            botoes.setAlignment(Pos.CENTER_RIGHT);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            card.getChildren().addAll(info, spacer, botoes);
            listaProjetos.getChildren().add(card);
        }

        // Remover a barra de rolagem
        listaProjetos.setPrefHeight(Region.USE_COMPUTED_SIZE); // Ajusta a altura para o conteúdo
        content.getChildren().add(listaProjetos); // Adiciona diretamente sem ScrollPane

        root.setLeft(menu);
        root.setCenter(content);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Projetos em Curso - Financeiro");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.show();
    }
}