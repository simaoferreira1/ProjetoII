package com.example.proj2.views;

import com.example.proj2.SpringContext;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Projeto;
import com.example.proj2.services.ProjetoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class ProjetosCursoEspecialistaView {

    private final Stage stage;
    private final Especialista especialista;

    public ProjetosCursoEspecialistaView(Stage stage) {
        this.stage = stage;
        this.especialista = null; // Mantido para compatibilidade
    }

    public ProjetosCursoEspecialistaView(Stage stage, Especialista especialista) {
        this.stage = stage;
        this.especialista = especialista;
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

        // Exibir o nome do especialista no formato "Especialista: Nome do Especialista"
        String nomeEspecialista = "Especialista Desconhecido";
        if (especialista != null && especialista.getNome() != null && !especialista.getNome().trim().isEmpty()) {
            nomeEspecialista = especialista.getNome();
        } else {
            // Log para depuração
            System.out.println("Especialista ou nome está null: " + (especialista == null ? "especialista é null" : "nome é null ou vazio"));
        }
        Label nome = new Label("Especialista: " + nomeEspecialista);
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        nome.setWrapText(true);

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

        Button btnProjetosCurso = criarBotao("🗂 Projetos\nem curso", estiloBtn, estiloHover);
        btnProjetosCurso.setOnAction(e -> new ProjetosCursoEspecialistaView(stage, especialista).show());

        Button btnProjetosOrcamento = criarBotao("💰 Projetos\nem pré-planeamento", estiloBtn, estiloHover);
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoEspecialistaView(stage, especialista).show());

        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);
        btnLogout.setOnAction(e -> stage.close());

        conteudoMenu.getChildren().addAll(nome, btnProjetosCurso, btnProjetosOrcamento);

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);
        layout.setLeft(menu);

        // === CONTEÚDO CENTRAL ===
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));

        Label titulo = new Label("Projetos em Curso");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        conteudo.getChildren().add(titulo);

        ProjetoService service = SpringContext.getBean(ProjetoService.class);
        List<Projeto> projetos = service.listarProjetos();

        // Filtrar projetos no estado "em curso"
        List<Projeto> projetosFiltrados = projetos.stream()
                .filter(p -> p != null && p.getEstado() != null && "em curso".equalsIgnoreCase(p.getEstado().trim()))
                .toList();

        if (projetosFiltrados.isEmpty()) {
            Label semProjetosLabel = new Label("Nenhum projeto em curso disponível.");
            semProjetosLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");
            conteudo.getChildren().add(semProjetosLabel);
        } else {
            VBox lista = new VBox(10);
            for (Projeto projeto : projetosFiltrados) {
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
                btnAbrir.setOnAction(e -> new DetalhesProjetoCursoEspecialistaView(projeto).show());

                Button btnEliminar = criarBotaoAcao("🗑 Eliminar", true);
                btnEliminar.setOnAction(e -> {
                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacao.setTitle("Confirmação");
                    confirmacao.setHeaderText("Tem certeza que deseja eliminar este projeto?");
                    confirmacao.setContentText("Esta ação é irreversível.");

                    confirmacao.showAndWait().ifPresent(resposta -> {
                        if (resposta == ButtonType.OK) {
                            ProjetoService serviceInterno = SpringContext.getBean(ProjetoService.class);
                            serviceInterno.removerProjeto(projeto.getId());

                            Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                            sucesso.setTitle("Sucesso");
                            sucesso.setHeaderText(null);
                            sucesso.setContentText("Projeto eliminado com sucesso!");
                            sucesso.showAndWait();

                            show();
                        }
                    });
                });

                HBox botoes = new HBox(10, btnAbrir, btnEliminar);
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
        stage.setTitle("Projetos em Curso");
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