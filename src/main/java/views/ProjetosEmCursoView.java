package com.example.proj2.views;

import com.example.proj2.SpringContext;
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

import java.util.List;

public class ProjetosEmCursoView {

    private final Stage stage;

    public ProjetosEmCursoView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();

        // === MENU LATERAL ===
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Gestor");
        nome.setStyle("-fx-font-size: 16px;");

        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333; -fx-font-size: 14px; -fx-pref-width: 180px; -fx-pref-height: 60px;";

        Button btnSolicitacoes = new Button("📋 Solicitações de Projeto");
        Button btnProjetosCurso = new Button("🗂 Projetos em curso");
        Button btnProjetosOrcamento = new Button("💰 Projetos para orçamento");
        Button btnProjetosEspera = new Button("🕒 Projetos em espera");
        Button btnLogout = new Button("↩ Log Out");

        for (Button btn : new Button[]{btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout}) {
            btn.setStyle(estiloBtn);
            btn.setWrapText(true);
        }

        menu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout);

        // === CONTEÚDO CENTRAL ===
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 20, 20, 20));

        Label titulo = new Label("Projetos em curso");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        content.getChildren().add(titulo);

        VBox listaProjetos = new VBox(10);
        ProjetoService service = SpringContext.getBean(ProjetoService.class);
        List<Projeto> projetos = service.listarProjetos().stream()
                .filter(p -> "em andamento".equalsIgnoreCase(p.getEstado()))
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

            Button btnAbrir = new Button("Abrir");
            Button btnEliminar = new Button("🗑 Eliminar projeto");
            btnEliminar.setStyle("-fx-text-fill: red;");

            HBox botoes = new HBox(10, btnAbrir, btnEliminar);
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
        stage.setTitle("Projetos em curso");
        stage.show();
    }
}

