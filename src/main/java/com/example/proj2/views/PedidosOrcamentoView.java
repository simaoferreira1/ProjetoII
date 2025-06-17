package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.models.Orcamentoprojeto;
import com.example.proj2.models.Projeto;
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

public class PedidosOrcamentoView {

    private final Stage stage;
    private final Membrodepartamentofinanceiro financeiro;

    public PedidosOrcamentoView(Stage stage, Membrodepartamentofinanceiro financeiro) {
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

        Label nome = new Label("👤 Financeiro: " + (financeiro != null && financeiro.getNome() != null ? financeiro.getNome() : "Desconhecido"));
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

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

        menu.getChildren().addAll(nome, btnPedidosOrcamento, btnProjetosCurso, spacerMenu, btnSair);
        root.setLeft(menu);

        // === CONTEÚDO CENTRAL ===
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 20, 20, 20));

        Label titulo = new Label("Pedidos de Orçamento");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        content.getChildren().add(titulo);

        VBox listaPedidos = new VBox(10);
        ProjetoService projetoService = SpringContext.getBean(ProjetoService.class);
        OrcamentoprojetoService orcamentoService = SpringContext.getBean(OrcamentoprojetoService.class);
        List<Projeto> pedidos = projetoService.listarProjetos();

        // Filtrar projetos no estado "pendente de orçamento"
        List<Projeto> pedidosFiltrados = pedidos.stream()
                .filter(p -> p != null && p.getEstado() != null && "pendente de orçamento".equalsIgnoreCase(p.getEstado().trim()))
                .toList();

        List<Orcamentoprojeto> orcamentos = orcamentoService.listarOrcamentoprojetos();

        if (pedidosFiltrados.isEmpty()) {
            Label semPedidos = new Label("Nenhum pedido de orçamento disponível.");
            semPedidos.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");
            listaPedidos.getChildren().add(semPedidos);
        } else {
            for (Projeto projeto : pedidosFiltrados) {
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

                Button btnConsultar = criarBotaoAcao("Consultar", false);
                Button btnAprovar = criarBotaoAcao("Aprovar\nOrçamento", false);

                btnConsultar.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Detalhes do Pedido de Orçamento");
                    alert.setHeaderText(projeto.getNome() != null ? projeto.getNome() : "N/A");
                    String nomeGestor = projeto.getGestordeprojeto() != null && projeto.getGestordeprojeto().getNome() != null ?
                            projeto.getGestordeprojeto().getNome() : "Desconhecido";
                    alert.setContentText("Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A") + "\n" +
                            "Data de Início: " + (projeto.getDatainicio() != null ? projeto.getDatainicio() : "N/A") + "\n" +
                            "Data de Fim Prevista: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista() : "N/A") + "\n" +
                            "Localização: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N/A") + "\n" +
                            "Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "N/A") + "\n" +
                            "Orçamento Proposto: €" + orcamentoAtual + "\n" +
                            "Gestor Responsável: " + nomeGestor);
                    alert.showAndWait();
                });

                btnAprovar.setOnAction(event -> {
                    if (orcamentoAtual.compareTo(BigDecimal.ZERO) <= 0) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erro");
                        error.setHeaderText(null);
                        error.setContentText("O orçamento deve ser maior que zero para ser aprovado.");
                        error.showAndWait();
                        return;
                    }

                    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmAlert.setTitle("Aprovar Orçamento");
                    confirmAlert.setHeaderText("Confirmar Aprovação");
                    confirmAlert.setContentText("Deseja aprovar o orçamento de €" + orcamentoAtual + " para o projeto \"" + (projeto.getNome() != null ? projeto.getNome() : "N/A") + "\"?");

                    Optional<ButtonType> result = confirmAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            Orcamentoprojeto orcamento;
                            if (orcamentoOpt.isPresent()) {
                                orcamento = orcamentoOpt.get();
                                orcamento.setDataaprovacao(LocalDate.now());
                                orcamento.setEstado("aprovado");
                                orcamentoService.atualizarOrcamentoprojeto(orcamento);
                            } else {
                                orcamento = new Orcamentoprojeto();
                                orcamento.setValortotal(orcamentoAtual);
                                orcamento.setDataaprovacao(LocalDate.now());
                                orcamento.setEstado("aprovado");
                                orcamento.setProjeto(projeto);
                                orcamentoService.salvarOrcamentoprojeto(orcamento);
                            }

                            projeto.setEstado("em curso");
                            projetoService.atualizarProjeto(projeto);

                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                            success.setTitle("Sucesso");
                            success.setHeaderText(null);
                            success.setContentText("Orçamento aprovado e projeto movido para 'em curso'!");
                            success.showAndWait();

                            show();
                        } catch (IllegalArgumentException e) {
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setTitle("Erro");
                            error.setHeaderText(null);
                            error.setContentText(e.getMessage());
                            error.showAndWait();
                        }
                    }
                });

                HBox botoes = new HBox(10, btnConsultar, btnAprovar);
                botoes.setAlignment(Pos.CENTER_RIGHT);

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                card.getChildren().addAll(info, spacer, botoes);
                listaPedidos.getChildren().add(card);
            }
        }

        ScrollPane scroll = new ScrollPane(listaPedidos);
        scroll.setFitToWidth(true);
        content.getChildren().add(scroll);

        root.setCenter(content);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Pedidos de Orçamento - Financeiro");
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