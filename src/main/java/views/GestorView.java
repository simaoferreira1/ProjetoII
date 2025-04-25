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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        nome.setFont(new Font(16));

        // Estilo comum para botões
        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-pref-width: 180px; -fx-pref-height: 80px;";

        Button btnSolicitacoes = new Button("📋 Solicitações \nde Projeto");
        Button btnProjetosCurso = new Button("🗂 Projetos \nem curso");
        Button btnProjetosOrcamento = new Button("💰 Projetos \npara orçamento");
        Button btnProjetosEspera = new Button("🕒 Projetos \nem espera");
        Button btnLogout = new Button("↩ Log Out");

        // Aplica o mesmo estilo a todos os botões
        for (Button btn : new Button[]{btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout}) {
            btn.setStyle(estiloBtn);
        }

        menu.getChildren().addAll(nome, btnSolicitacoes, btnProjetosCurso, btnProjetosOrcamento, btnProjetosEspera, btnLogout);

        // ==== CONTEÚDO CENTRAL ====
        VBox center = new VBox(10);
        center.setAlignment(Pos.CENTER);

        // Logo
        ImageView imageView = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        imageView.setFitHeight(400); // imagem maior
        imageView.setPreserveRatio(true);
        imageView.setOpacity(0.3); // opacidade reduzida

        center.getChildren().add(imageView);

        // Adiciona ao layout principal
        root.setLeft(menu);
        root.setCenter(center);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Painel do Gestor");
        stage.show();
    }
}
