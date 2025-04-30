package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Projeto;
import com.example.proj2.services.ProjetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class ProjetosPrePlaneamentoView {
    private final Stage stage;
    private final Gestordeprojeto gestor;

    public ProjetosPrePlaneamentoView(Stage stage, Gestordeprojeto gestor) {
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

        Label nome = new Label("👤 Gestor: " + (gestor != null && gestor.getNome() != null ? gestor.getNome() : "Desconhecido"));
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
        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);

        conteudoMenu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosPrePlaneamento);

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);

        btnSolicitacoes.setOnAction(e -> new SolicitacoesView(stage, gestor).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoView(stage, gestor).show());
        btnProjetosPrePlaneamento.setOnAction(e -> new ProjetosPrePlaneamentoView(stage, gestor).show());
        btnLogout.setOnAction(e -> stage.close());

        layout.setLeft(menu);

        // === CONTEÚDO CENTRAL ===
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));

        Label titulo = new Label("Projetos em Pré-Planeamento");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        conteudo.getChildren().add(titulo);

        ProjetoService service = SpringContext.getBean(ProjetoService.class);
        List<Projeto> projetos = service.listarProjetos();

        List<Projeto> projetosPrePlaneamento = projetos.stream()
                .filter(p -> p != null && p.getEstado() != null && "em pré-planeamento".equalsIgnoreCase(p.getEstado().trim()))
                .toList();

        if (projetosPrePlaneamento.isEmpty()) {
            Label semProjetos = new Label("Nenhum projeto em pré-planeamento encontrado.");
            semProjetos.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
            conteudo.getChildren().add(semProjetos);
        } else {
            VBox lista = new VBox(10);
            lista.setStyle("-fx-background-color: #f2f2f2; -fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #cccccc; -fx-border-width: 1px;");
            for (Projeto projeto : projetosPrePlaneamento) {
                HBox card = new HBox(15);
                card.setPadding(new Insets(10));
                card.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
                card.setAlignment(Pos.CENTER_LEFT);

                VBox info = new VBox(5);
                Label nomeProjeto = new Label("📌 Projeto: " + (projeto.getNome() != null ? projeto.getNome() : "N/A"));
                nomeProjeto.setStyle("-fx-font-weight: bold;");
                Label descricao = new Label("📝 Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A"));

                info.getChildren().addAll(nomeProjeto, descricao);

                Button btnAbrir = criarBotaoAcao("Abrir", false);
                btnAbrir.setOnAction(e -> new DetalhesProjetosEmCursoView(projeto, stage, gestor).show());

                Button btnPlaneado = criarBotaoAcao("✔ Planeado", true);
                btnPlaneado.setStyle("-fx-background-color: #ffffff; " +
                        "-fx-text-fill: green; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 6px 12px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-border-color: green; " +
                        "-fx-border-width: 1px; " +
                        "-fx-cursor: hand;");
                btnPlaneado.setOnMouseEntered(e -> btnPlaneado.setStyle(
                        "-fx-background-color: #ccffcc; " +
                                "-fx-text-fill: green; " +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 6px 12px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-border-color: green; " +
                                "-fx-border-width: 1px; " +
                                "-fx-cursor: hand; " +
                                "-fx-scale-x: 1.05; " +
                                "-fx-scale-y: 1.05;"));
                btnPlaneado.setOnMouseExited(e -> btnPlaneado.setStyle(
                        "-fx-background-color: #ffffff; " +
                                "-fx-text-fill: green; " +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 6px 12px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-border-color: green; " +
                                "-fx-border-width: 1px; " +
                                "-fx-cursor: hand;"));
                btnPlaneado.setOnAction(e -> planejarProjeto(projeto));

                HBox botoes = new HBox(10, btnAbrir, btnPlaneado);
                botoes.setAlignment(Pos.CENTER_RIGHT);

                Region espaco = new Region();
                HBox.setHgrow(espaco, Priority.ALWAYS);

                card.getChildren().addAll(info, espaco, botoes);
                lista.getChildren().add(card);
            }
            conteudo.getChildren().add(lista);
        }

        layout.setCenter(conteudo);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Projetos em Pré-Planeamento");
        stage.show();
    }

    private void planejarProjeto(Projeto projeto) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Tem certeza que deseja marcar este projeto como planeado?");
        confirmacao.setContentText("O projeto passará para o estado 'em curso'.");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                ProjetoService service = SpringContext.getBean(ProjetoService.class);
                projeto.setEstado("em curso");
                service.atualizarProjeto(projeto);

                Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                sucesso.setTitle("Sucesso");
                sucesso.setHeaderText(null);
                sucesso.setContentText("Projeto marcado como 'em curso' com sucesso!");
                sucesso.showAndWait();

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

    private Button criarBotaoAcao(String texto, boolean verde) {
        String estilo = "-fx-background-color: #ffffff; " +
                "-fx-text-fill: " + (verde ? "green" : "#333333") + "; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 6px 12px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-color: " + (verde ? "green" : "#cccccc") + "; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;";
        Button button = new Button(texto);
        button.setStyle(estilo);

        button.setOnMouseEntered(e -> button.setStyle(estilo +
                "-fx-background-color: " + (verde ? "#ccffcc" : "#e0e0e0") + "; " +
                "-fx-scale-x: 1.05; " +
                "-fx-scale-y: 1.05;"));
        button.setOnMouseExited(e -> button.setStyle(estilo));
        return button;
    }
}