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

        // === MENU LATERAL ===
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Financeiro");
        nome.setStyle("-fx-font-size: 16px;");

        // Estilo para os botões com texto centralizado
        String estiloBtn = "-fx-around-color: #ffffff; " +
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

        btnProjetosCurso.setOnAction(event -> new ProjetosEmCursoFinanceiroView(stage, financeiro).show());
        btnSair.setOnAction(event -> stage.close());

        // Adicionar espaçador para empurrar o botão "Sair" para o final
        Region spacerMenu = new Region();
        VBox.setVgrow(spacerMenu, Priority.ALWAYS);

        menu.getChildren().addAll(nome, btnPedidosOrcamento, btnProjetosCurso, spacerMenu, btnSair);

        // === CONTEÚDO CENTRAL ===
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 20, 20, 20));

        Label titulo = new Label("Pedidos de Orçamento");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        content.getChildren().add(titulo);

        VBox listaPedidos = new VBox(10);
        ProjetoService projetoService = SpringContext.getBean(ProjetoService.class);
        OrcamentoprojetoService orcamentoService = SpringContext.getBean(OrcamentoprojetoService.class);
        List<Projeto> pedidos = projetoService.listarProjetos().stream()
                .filter(p -> "pendente de orçamento".equalsIgnoreCase(p.getEstado()))
                .toList();

        // Buscar todos os orçamentos para associar aos projetos
        List<Orcamentoprojeto> orcamentos = orcamentoService.listarOrcamentoprojetos();

        for (Projeto projeto : pedidos) {
            HBox card = new HBox(15);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px;");
            card.setAlignment(Pos.CENTER_LEFT);

            VBox info = new VBox(5);
            Label nomeProjeto = new Label(projeto.getNome());
            nomeProjeto.setStyle("-fx-font-weight: bold;");
            Label descricao = new Label(projeto.getDescricao());

            info.getChildren().addAll(nomeProjeto, descricao);

            // Buscar o orçamento do projeto (se existir)
            Optional<Orcamentoprojeto> orcamentoOpt = orcamentos.stream()
                    .filter(o -> o.getProjeto() != null && o.getProjeto().getId().equals(projeto.getId()))
                    .findFirst();
            BigDecimal orcamentoAtual = orcamentoOpt.map(Orcamentoprojeto::getValortotal).orElse(BigDecimal.ZERO);

            // Botões do card
            Button btnConsultar = new Button("Consultar");
            btnConsultar.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black;");

            Button btnAprovar = new Button("Aprovar Orçamento");

            // Ação do botão "Consultar"
            btnConsultar.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Detalhes do Pedido de Orçamento");
                alert.setHeaderText(projeto.getNome());
                alert.setContentText("Descrição: " + projeto.getDescricao() + "\n" +
                        "Data de Início: " + projeto.getDatainicio() + "\n" +
                        "Data de Fim Prevista: " + projeto.getDatafimprevista() + "\n" +
                        "Localização: " + projeto.getLocalizacao() + "\n" +
                        "Estado: " + projeto.getEstado() + "\n" +
                        "Orçamento Proposto: €" + orcamentoAtual + "\n" +
                        "Gestor Responsável: " + projeto.getGestordeprojeto().getNome());
                alert.showAndWait();
            });

            // Ação do botão "Aprovar Orçamento"
            btnAprovar.setOnAction(event -> {
                // Exibir mensagem de confirmação
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Aprovar Orçamento");
                confirmAlert.setHeaderText("Confirmar Aprovação");
                confirmAlert.setContentText("Deseja aprovar o orçamento de €" + orcamentoAtual + " para o projeto \"" + projeto.getNome() + "\"?");

                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        // Criar ou atualizar o orçamento
                        Orcamentoprojeto orcamento;
                        if (orcamentoOpt.isPresent()) {
                            orcamento = orcamentoOpt.get();
                            orcamento.setDataaprovacao(LocalDate.now());
                            orcamento.setEstado("aprovado");
                            orcamentoService.atualizarOrcamentoprojeto(orcamento);
                        } else {
                            // Se não houver orçamento, criar um novo com o valor zero (ou lançar erro, dependendo do requisito)
                            orcamento = new Orcamentoprojeto();
                            orcamento.setId(BigDecimal.valueOf(orcamentoService.listarOrcamentoprojetos().size() + 1));
                            orcamento.setValortotal(BigDecimal.ZERO); // Usar zero se não houver orçamento prévio
                            orcamento.setDataaprovacao(LocalDate.now());
                            orcamento.setEstado("aprovado");
                            orcamento.setProjeto(projeto);
                            orcamentoService.salvarOrcamentoprojeto(orcamento);
                        }

                        // Atualizar o estado do projeto para "em curso"
                        projeto.setEstado("em curso");
                        projetoService.atualizarProjeto(projeto);

                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Sucesso");
                        success.setHeaderText(null);
                        success.setContentText("Orçamento aprovado e projeto movido para 'em curso'!");
                        success.showAndWait();

                        show(); // Recarrega a página para atualizar a lista
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

        // Adicionar a lista diretamente ao content, sem ScrollPane
        content.getChildren().add(listaPedidos);

        root.setLeft(menu);
        root.setCenter(content);

        Scene scene = new Scene(root, 900, 600); // Definir tamanho fixo inicial
        stage.setScene(scene);
        stage.setTitle("Pedidos de Orçamento - Financeiro");
        stage.setMinWidth(900);  // Definir largura mínima
        stage.setMinHeight(600); // Definir altura mínima
        stage.setResizable(true);
        stage.show();
    }
}