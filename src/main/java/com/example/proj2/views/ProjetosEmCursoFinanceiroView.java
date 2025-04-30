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
        root.setStyle("-fx-background-color: white;");

        // === MENU LATERAL ===
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        // Exibir o nome e ID do financeiro
        Label nomeLabel = new Label("👤 Financeiro: " + (financeiro != null && financeiro.getNome() != null ? financeiro.getNome() : "Desconhecido"));
        nomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        nomeLabel.setWrapText(true);

        Label idFinanceiro = new Label("🆔 ID: " + (financeiro != null && financeiro.getId() != null ? financeiro.getId() : "N/A"));
        idFinanceiro.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");

        // Estilo para os botões
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
                "-fx-alignment: center;";

        String estiloHover = "-fx-background-color: #e0e0e0; " +
                "-fx-scale-x: 1.02; " +
                "-fx-scale-y: 1.02;";

        Button btnPedidosOrcamento = criarBotao("💰 Pedidos de\nOrçamento", estiloBtn, estiloHover);
        Button btnProjetosCurso = criarBotao("🗂 Projetos\nem curso", estiloBtn, estiloHover);
        Button btnSair = criarBotao("↩ Sair", estiloBtn, estiloHover);

        btnPedidosOrcamento.setOnAction(event -> new PedidosOrcamentoView(stage, financeiro).show());
        btnProjetosCurso.setOnAction(event -> new ProjetosEmCursoFinanceiroView(stage, financeiro).show());
        btnSair.setOnAction(event -> stage.close());

        Region spacerMenu = new Region();
        VBox.setVgrow(spacerMenu, Priority.ALWAYS);

        menu.getChildren().addAll(nomeLabel, idFinanceiro, btnPedidosOrcamento, btnProjetosCurso, spacerMenu, btnSair);
        root.setLeft(menu);

        // === CONTEÚDO CENTRAL ===
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 20, 20, 20));

        Label titulo = new Label("Projetos em Curso");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        content.getChildren().add(titulo);

        ProjetoService projetoService = SpringContext.getBean(ProjetoService.class);
        OrcamentoprojetoService orcamentoService = SpringContext.getBean(OrcamentoprojetoService.class);
        PagamentoRepository pagamentoRepository = SpringContext.getBean(PagamentoRepository.class);
        List<Projeto> projetos = projetoService.listarProjetos();

        // Filtrar projetos no estado "em curso"
        List<Projeto> projetosFiltrados = projetos.stream()
                .filter(p -> p != null && p.getEstado() != null && "em curso".equalsIgnoreCase(p.getEstado().trim()))
                .toList();

        List<Orcamentoprojeto> orcamentos = orcamentoService.listarOrcamentoprojetos();

        if (projetosFiltrados.isEmpty()) {
            Label semProjetosLabel = new Label("Nenhum projeto em curso disponível.");
            semProjetosLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");
            content.getChildren().add(semProjetosLabel);
        } else {
            VBox listaProjetos = new VBox(10);

            for (Projeto projeto : projetosFiltrados) {
                HBox card = new HBox(15);
                card.setPadding(new Insets(10));
                card.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
                card.setAlignment(Pos.CENTER_LEFT);

                VBox info = new VBox(5);
                Label idProjeto = new Label("🆔 ID: " + (projeto.getId() != null ? projeto.getId() : "N/A"));
                Label nomeProjeto = new Label("📌 Projeto: " + (projeto.getNome() != null ? projeto.getNome() : "N/A"));
                nomeProjeto.setStyle("-fx-font-weight: bold;");
                Label descricao = new Label("📝 Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A"));

                info.getChildren().addAll(idProjeto, nomeProjeto, descricao);

                Optional<Orcamentoprojeto> orcamentoOpt = orcamentos.stream()
                        .filter(o -> o != null && o.getProjeto() != null && o.getProjeto().getId() != null && o.getProjeto().getId().equals(projeto.getId()))
                        .findFirst();
                BigDecimal orcamentoAtual = orcamentoOpt.map(Orcamentoprojeto::getValortotal).orElse(BigDecimal.ZERO);

                Button btnAbrir = criarBotaoAcao("Abrir", false);
                Button btnAlterarOrcamento = criarBotaoAcao("Alterar\nOrçamento", false);
                Button btnGerarRelatorio = criarBotaoAcao("Gerar\nRelatório", false);

                btnAbrir.setOnAction(event -> {
                    Cliente cliente = projeto.getIdcliente();
                    BigDecimal entradasFinanceiras = BigDecimal.ZERO;
                    if (cliente != null) {
                        List<Pagamento> pagamentos = pagamentoRepository.findByCliente(cliente);
                        entradasFinanceiras = pagamentos.stream()
                                .map(Pagamento::getValor)
                                .filter(valor -> valor != null)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                    }

                    BigDecimal saidasFinanceiras = orcamentoAtual;
                    BigDecimal pagamentosPendentes = orcamentoAtual.subtract(entradasFinanceiras);

                    String nomeGestor = "Desconhecido";
                    Gestordeprojeto gestor = projeto.getGestordeprojeto();
                    if (gestor != null && gestor.getNome() != null) {
                        nomeGestor = gestor.getNome();
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Detalhes do Projeto");
                    alert.setHeaderText(projeto.getNome() != null ? projeto.getNome() : "N/A");
                    alert.setContentText("Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A") + "\n" +
                            "Data de Início: " + (projeto.getDatainicio() != null ? projeto.getDatainicio() : "N/A") + "\n" +
                            "Data de Fim Prevista: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista() : "N/A") + "\n" +
                            "Localização: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N/A") + "\n" +
                            "Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "N/A") + "\n" +
                            "Entradas Financeiras: €" + entradasFinanceiras + "\n" +
                            "Saídas Financeiras: €" + saidasFinanceiras + "\n" +
                            "Pagamentos Pendentes: €" + pagamentosPendentes + "\n" +
                            "Pagamentos Realizados: €" + entradasFinanceiras + "\n" +
                            "Gestor Encarregue: " + nomeGestor + "\n" +
                            "Orçamento: €" + orcamentoAtual);
                    alert.showAndWait();
                });

                btnAlterarOrcamento.setOnAction(event -> {
                    TextInputDialog dialog = new TextInputDialog(orcamentoAtual.toString());
                    dialog.setTitle("Alterar Orçamento");
                    dialog.setHeaderText("Digite o novo orçamento para o projeto \"" + (projeto.getNome() != null ? projeto.getNome() : "N/A") + "\":");
                    dialog.setContentText("Novo Orçamento (€):");

                    dialog.showAndWait().ifPresent(novoOrcamento -> {
                        try {
                            BigDecimal novoValor = new BigDecimal(novoOrcamento);

                            Orcamentoprojeto orcamento;
                            if (orcamentoOpt.isPresent()) {
                                orcamento = orcamentoOpt.get();
                                orcamento.setValortotal(novoValor);
                                orcamento.setDataaprovacao(LocalDate.now());
                                orcamento.setEstado("aprovado");
                                orcamentoService.atualizarOrcamentoprojeto(orcamento);
                            } else {
                                orcamento = new Orcamentoprojeto();
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

                            show();
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

                btnGerarRelatorio.setOnAction(event -> {
                    Cliente cliente = projeto.getIdcliente();
                    BigDecimal entradasFinanceiras = BigDecimal.ZERO;
                    if (cliente != null) {
                        List<Pagamento> pagamentos = pagamentoRepository.findByCliente(cliente);
                        entradasFinanceiras = pagamentos.stream()
                                .map(Pagamento::getValor)
                                .filter(valor -> valor != null)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                    }

                    BigDecimal saidasFinanceiras = orcamentoAtual;
                    BigDecimal percentualOrcamentoUtilizado = orcamentoAtual.compareTo(BigDecimal.ZERO) > 0 ?
                            saidasFinanceiras.divide(orcamentoAtual, 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)) :
                            BigDecimal.ZERO;

                    BigDecimal progresso = new BigDecimal("40.00");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Relatório Financeiro de Progresso");
                    alert.setHeaderText("Relatório para o projeto \"" + (projeto.getNome() != null ? projeto.getNome() : "N/A") + "\"");
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

            ScrollPane scroll = new ScrollPane(listaProjetos);
            scroll.setFitToWidth(true);
            content.getChildren().add(scroll);
        }

        root.setCenter(content);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Projetos em Curso - Financeiro");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.show();
    }

    private Button criarBotao(String texto, String estiloBase, String estiloHover) {
        Button button = new Button(texto);
        button.setWrapText(true);
        button.setStyle(estiloBase);
        button.setOnMouseEntered(e -> button.setStyle(estiloBase + estiloHover));
        button.setOnMouseExited(e -> button.setStyle(estiloBase));
        return button;
    }

    private Button criarBotaoAcao(String texto, boolean cinza) {
        String estilo = "-fx-background-color: " + (cinza ? "#e0e0e0" : "#ffffff") + "; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 6px 12px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: #cccccc; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;";
        Button button = new Button(texto);
        button.setWrapText(true);
        button.setStyle(estilo);

        button.setOnMouseEntered(e -> button.setStyle(estilo +
                "-fx-background-color: " + (cinza ? "#d0d0d0" : "#e0e0e0") + "; " +
                "-fx-scale-x: 1.05; " +
                "-fx-scale-y: 1.05;"));
        button.setOnMouseExited(e -> button.setStyle(estilo));
        return button;
    }
}