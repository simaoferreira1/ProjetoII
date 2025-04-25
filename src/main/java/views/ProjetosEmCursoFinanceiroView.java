package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.models.Projeto;
import com.example.proj2.services.ProjetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;

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

        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333; -fx-font-size: 14px; -fx-pref-width: 180px; -fx-pref-height: 60px;";

        Button btnPedidosOrcamento = new Button("💰 Pedidos de Orçamento");
        Button btnProjetosCurso = new Button("🗂 Projetos em Curso");
        Button btnLogout = new Button("↩ Log Out");

        for (Button btn : new Button[]{btnPedidosOrcamento, btnProjetosCurso, btnLogout}) {
            btn.setStyle(estiloBtn);
            btn.setWrapText(true);
        }

        btnPedidosOrcamento.setOnAction(event -> new ProjetosOrcamentoView(stage).show());
        btnLogout.setOnAction(event -> stage.close());

        menu.getChildren().addAll(nome, btnPedidosOrcamento, btnProjetosCurso, btnLogout);

        // === CONTEÚDO CENTRAL ===
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 20, 20, 20));

        Label titulo = new Label("Projetos em Curso");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        content.getChildren().add(titulo);

        VBox listaProjetos = new VBox(10);
        ProjetoService service = SpringContext.getBean(ProjetoService.class);
        List<Projeto> projetos = service.listarProjetos().stream()
                .filter(p -> "em curso".equalsIgnoreCase(p.getEstado()))
                .toList();

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

            // Botões do card
            Button btnAbrir = new Button("Abrir");
            Button btnAlterarOrcamento = new Button("Alterar Orçamento");
            Button btnGerarRelatorio = new Button("Gerar Relatório");

            // Ação do botão "Abrir" (Consultar Entradas/Saídas e Verificar Pagamentos)
            btnAbrir.setOnAction(event -> {
                // Valores fictícios para simulação
                BigDecimal entradasFinanceiras = new BigDecimal("50000.00");
                BigDecimal saidasFinanceiras = new BigDecimal("30000.00");
                BigDecimal pagamentosPendentes = new BigDecimal("10000.00");

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
                        "Pagamentos Pendentes: €" + pagamentosPendentes);
                alert.showAndWait();
            });

            // Ação do botão "Alterar Orçamento" (Simulação sem persistência)
            btnAlterarOrcamento.setOnAction(event -> {
                // Valor fictício inicial para o orçamento
                BigDecimal orcamentoAtual = new BigDecimal("75000.00");

                TextInputDialog dialog = new TextInputDialog(orcamentoAtual.toString());
                dialog.setTitle("Alterar Orçamento");
                dialog.setHeaderText("Digite o novo orçamento para o projeto \"" + projeto.getNome() + "\":");
                dialog.setContentText("Novo Orçamento (€):");

                dialog.showAndWait().ifPresent(novoOrcamento -> {
                    try {
                        BigDecimal novoValor = new BigDecimal(novoOrcamento);

                        // Simulação: apenas mostramos uma mensagem, sem persistência
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Sucesso");
                        success.setHeaderText(null);
                        success.setContentText("Orçamento alterado para €" + novoValor + " (simulação, não persistido).");
                        success.showAndWait();
                    } catch (NumberFormatException e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erro");
                        error.setHeaderText(null);
                        error.setContentText("Por favor, insira um valor numérico válido.");
                        error.showAndWait();
                    }
                });
            });

            // Ação do botão "Gerar Relatório" (Criar Relatório Financeiro de Progresso)
            btnGerarRelatorio.setOnAction(event -> {
                // Valores fictícios para simulação
                BigDecimal entradasFinanceiras = new BigDecimal("50000.00");
                BigDecimal saidasFinanceiras = new BigDecimal("30000.00");
                BigDecimal orcamento = new BigDecimal("75000.00");
                BigDecimal progresso = new BigDecimal("40.00");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Relatório Financeiro de Progresso");
                alert.setHeaderText("Relatório para o projeto \"" + projeto.getNome() + "\"");
                alert.setContentText("Entradas Financeiras: €" + entradasFinanceiras + "\n" +
                        "Saídas Financeiras: €" + saidasFinanceiras + "\n" +
                        "Saldo (Entradas - Saídas): €" + entradasFinanceiras.subtract(saidasFinanceiras) + "\n" +
                        "Orçamento Total: €" + orcamento + "\n" +
                        "Percentual do Orçamento Utilizado: " +
                        (orcamento.compareTo(BigDecimal.ZERO) > 0 ?
                                saidasFinanceiras.divide(orcamento, 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)) : BigDecimal.ZERO) + "%\n" +
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

        // === TOPO COM LOGO ===
        VBox topo = new VBox();
        topo.setAlignment(Pos.TOP_RIGHT);
        topo.setPadding(new Insets(10));

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(90);
        logo.setPreserveRatio(true);

        topo.getChildren().add(logo);

        root.setLeft(menu);
        root.setCenter(content);
        root.setTop(topo);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Projetos em Curso - Financeiro");
        stage.show();
    }
}