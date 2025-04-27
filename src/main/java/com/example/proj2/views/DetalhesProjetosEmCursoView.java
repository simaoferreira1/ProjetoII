package com.example.proj2.views;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Projeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DetalhesProjetosEmCursoView {

    private final Projeto projeto;
    private final Stage stage;
    private final Gestordeprojeto gestor;
    private String observacoes;

    public DetalhesProjetosEmCursoView(Projeto projeto, Stage stage, Gestordeprojeto gestor) {
        this.projeto = projeto;
        this.stage = stage;
        this.gestor = gestor;
        this.observacoes = "";
    }

    public void show() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: white;");

        // === MENU LATERAL ===
        VBox menu = new VBox();
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        VBox conteudoMenu = new VBox(20);
        conteudoMenu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Gestor: " + (gestor != null ? gestor.getNome() : "Desconhecido"));
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: #ffffff; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-pref-width: 160px; " +
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

        Button btnSolicitacoes = criarBotao("📋 Solicitações\nde Projeto", estiloBtn, estiloHover);
        Button btnProjetosCurso = criarBotao("📂 Projetos\nem Curso", estiloBtn, estiloHover);
        Button btnProjetosPrePlaneamento = criarBotao("📝 Projetos em\nPré-Planeamento", estiloBtn, estiloHover);
        Button btnRegistrarCliente = criarBotao("👥 Registrar\nCliente", estiloBtn, estiloHover);
        Button btnRegistrarProjeto = criarBotao("📋 Registrar\nProjeto", estiloBtn, estiloHover);
        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);

        conteudoMenu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosPrePlaneamento, btnRegistrarCliente, btnRegistrarProjeto);

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);

        btnSolicitacoes.setOnAction(e -> new SolicitacoesView(stage, gestor).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoView(stage, gestor).show());
        btnProjetosPrePlaneamento.setOnAction(e -> new ProjetosPrePlaneamentoView(stage, gestor).show());
        btnRegistrarCliente.setOnAction(e -> new GestorView(stage, gestor).show());
        btnRegistrarProjeto.setOnAction(e -> new GestorView(stage, gestor).show());
        btnLogout.setOnAction(e -> stage.close());

        layout.setLeft(menu);

        // === CONTEÚDO CENTRAL ===
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(30));
        conteudo.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("📋 Detalhes do Projeto");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        VBox infoBox = new VBox(10);
        infoBox.getChildren().addAll(
                new Label("📌 Nome: " + (projeto.getNome() != null ? projeto.getNome() : "N/A")),
                new Label("📝 Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A")),
                new Label("⚙️ Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "N/A")),
                new Label("📅 Início: " + (projeto.getDatainicio() != null ? projeto.getDatainicio() : "N/A")),
                new Label("📅 Fim Previsto: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista() : "N/A")),
                new Label("📍 Localização: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N/A")),
                new Label("👤 Cliente: " + (projeto.getIdcliente() != null ? projeto.getIdcliente().getNome() : "N/A")),
                new Label("📜 Licenças: [Não disponível]"),
                new Label("📈 Progresso: [Não disponível]"),
                new Label("📋 Requisitos: [Não disponível]"),
                new Label("💰 Orçamento: [Não disponível]"),
                new Label("📊 Relatórios Financeiros: [Não disponível]")
        );

        // Seção de Observações
        Label labelObservacoes = new Label("📝 Observações:");
        labelObservacoes.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea observacoesField = new TextArea();
        observacoesField.setText(observacoes);
        observacoesField.setPromptText("Digite suas observações aqui...");
        observacoesField.setPrefRowCount(5);
        observacoesField.setPrefColumnCount(40);

        Button btnSalvarObservacoes = criarBotaoAcao("Salvar Observações", false);
        btnSalvarObservacoes.setOnAction(e -> {
            observacoes = observacoesField.getText();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Observações Salvas");
            alert.setHeaderText(null);
            alert.setContentText("Observações salvas com sucesso!\n\n" + observacoes);
            alert.showAndWait();
        });

        // Botão para Agendar Visita
        Button btnAgendarVisita = criarBotaoAcao("📅 Agendar Visita", false);
        btnAgendarVisita.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Visita Agendada");
            alert.setHeaderText(null);
            alert.setContentText("Visita agendada para o projeto " + projeto.getNome() + " com o cliente " +
                    (projeto.getIdcliente() != null ? projeto.getIdcliente().getNome() : "N/A"));
            alert.showAndWait();
        });

        conteudo.getChildren().addAll(titulo, infoBox, labelObservacoes, observacoesField, btnSalvarObservacoes, btnAgendarVisita);
        layout.setCenter(conteudo);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Detalhes do Projeto");
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

    private Button criarBotaoAcao(String texto, boolean vermelho) {
        String estilo = "-fx-background-color: #ffffff; " +
                "-fx-text-fill: " + (vermelho ? "red" : "#333333") + "; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 6px 12px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: " + (vermelho ? "red" : "#cccccc") + "; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;";
        Button button = new Button(texto);
        button.setStyle(estilo);

        button.setOnMouseEntered(e -> button.setStyle(estilo +
                "-fx-background-color: " + (vermelho ? "#ffcccc" : "#e0e0e0") + "; " +
                "-fx-scale-x: 1.05; " +
                "-fx-scale-y: 1.05;"));
        button.setOnMouseExited(e -> button.setStyle(estilo));
        return button;
    }
}