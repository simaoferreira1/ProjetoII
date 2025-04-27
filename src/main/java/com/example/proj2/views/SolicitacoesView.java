package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.services.SolicitacaoprojetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class SolicitacoesView {

    private final Stage stage;
    private final Gestordeprojeto gestor;

    public SolicitacoesView(Stage stage, Gestordeprojeto gestor) {
        this.stage = stage;
        this.gestor = gestor;
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

        Button btnRegistrarCliente = criarBotao("👥 Registrar\nCliente", estiloBtn, estiloHover);
        Button btnRegistrarProjeto = criarBotao("📋 Registrar\nProjeto", estiloBtn, estiloHover);
        Button btnSolicitacoes = criarBotao("📋 Solicitações\nde Projeto", estiloBtn, estiloHover);
        Button btnProjetosCurso = criarBotao("📂 Projetos\nem Curso", estiloBtn, estiloHover);
        Button btnProjetosPrePlaneamento = criarBotao("📝 Projetos em\nPré-Planeamento", estiloBtn, estiloHover);
        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);

        conteudoMenu.getChildren().addAll(nome, btnRegistrarCliente, btnRegistrarProjeto, btnSolicitacoes, btnProjetosCurso, btnProjetosPrePlaneamento);

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
        conteudo.setPadding(new Insets(20));

        Label titulo = new Label("Projetos Solicitados");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        conteudo.getChildren().add(titulo);

        SolicitacaoprojetoService service = SpringContext.getBean(SolicitacaoprojetoService.class);
        List<Solicitacaoprojeto> solicitacoes = service.listarSolicitacoes();

        if (solicitacoes.isEmpty()) {
            Label semSolicitacoes = new Label("Nenhuma solicitação de projeto encontrada.");
            semSolicitacoes.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
            conteudo.getChildren().add(semSolicitacoes);
        } else {
            VBox lista = new VBox(10);
            for (Solicitacaoprojeto sp : solicitacoes) {
                HBox card = new HBox(15);
                card.setPadding(new Insets(10));
                card.setStyle("-fx-background-color: white;");
                card.setAlignment(Pos.CENTER_LEFT);

                VBox info = new VBox(5);
                Label nomeProjeto = new Label("Projeto ID: " + sp.getId());
                nomeProjeto.setStyle("-fx-font-weight: bold;");
                Label descricao = new Label("Local: " + (sp.getLocalreuniao() != null ? sp.getLocalreuniao() : "N/A"));

                info.getChildren().addAll(nomeProjeto, descricao);

                Button btnAbrir = criarBotaoAcao("Abrir", false);
                btnAbrir.setOnAction(e -> new DetalhesSolicitacaoView(stage, sp).show());

                Button btnEliminar = criarBotaoAcao("🗑 Eliminar projeto", true);
                btnEliminar.setOnAction(e -> eliminarProjeto(sp));

                HBox botoes = new HBox(10, btnAbrir, btnEliminar);
                botoes.setAlignment(Pos.CENTER_RIGHT);

                Region espaco = new Region();
                HBox.setHgrow(espaco, Priority.ALWAYS);

                card.getChildren().addAll(info, espaco, botoes);
                lista.getChildren().add(card);
            }

            ScrollPane scroll = new ScrollPane(lista);
            scroll.setFitToWidth(true);
            conteudo.getChildren().add(scroll);
        }

        layout.setCenter(conteudo);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Solicitações de Projeto");
        stage.show();
    }

    private void eliminarProjeto(Solicitacaoprojeto sp) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Tem certeza que deseja eliminar esta solicitação?");
        confirmacao.setContentText("Esta ação é irreversível.");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                SolicitacaoprojetoService service = SpringContext.getBean(SolicitacaoprojetoService.class);
                service.eliminarSolicitacao(sp.getId());
                show();
            }
        });
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