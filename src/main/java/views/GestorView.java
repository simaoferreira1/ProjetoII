package com.example.proj2.views;

import com.example.proj2.models.Gestordeprojeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GestorView {
    private final Stage stage;
    private final Gestordeprojeto gestor;

    public GestorView(Stage stage, Gestordeprojeto gestor) {
        this.stage = stage;
        this.gestor = gestor;
    }

    public void show() {
        BorderPane root = new BorderPane();

        // ==== MENU LATERAL ====
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Gestor");
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; " +
                "-fx-font-size: 14px; -fx-pref-width: 180px; -fx-pref-height: 80px;";

        Button btnSolicitacoes = new Button("📋 Solicitações de Projeto");
        Button btnProjetosCurso = new Button("🗂 Projetos em curso");
        Button btnProjetosOrcamento = new Button("💰 Projetos para orçamento");
        Button btnProjetosEspera = new Button("🕒 Projetos em espera");
        Button btnLogout = new Button("↩ Log Out");

        for (Button btn : new Button[]{btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout}) {
            btn.setStyle(estiloBtn);
        }

        // Navegação dos botões
        btnSolicitacoes.setOnAction(e -> new SolicitacoesView(stage).show());
        btnProjetosCurso.setOnAction(e -> new ProjetosEmCursoView(stage).show());
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoView(stage).show());
        btnProjetosEspera.setOnAction(e -> new ProjetosEmEsperaView(stage).show());
        btnLogout.setOnAction(e -> stage.close());

        menu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout);

        // ==== LOGO CENTRAL ====
        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(10));
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(400);
        logo.setPreserveRatio(true);
        logo.setOpacity(0.3);
        center.getChildren().add(logo);

        root.setLeft(menu);
        root.setCenter(center);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Painel do Gestor");
        stage.show();
    }
}
