package com.example.proj2.views;

import com.example.proj2.models.Especialista;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EspecialistaView {

    private final Stage stage;
    private final Especialista especialista;

    public EspecialistaView(Stage stage, Especialista especialista) {
        this.stage = stage;
        this.especialista = especialista;
    }

    public void show() {
        BorderPane root = new BorderPane();

        // ==== MENU LATERAL ====
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Especialista");
        nome.setFont(new Font(16));

        // Estilo comum para botões
        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-pref-width: 180px; -fx-pref-height: 80px;";

        Button btnProjetosOrcamento = new Button("💰 Projetos \npara orçamento");
        Button btnProjetosCurso = new Button("🗂 Projetos \nem curso");
        Button btnLogout = new Button("↩ Log Out");

        // Aplica o mesmo estilo a todos os botões
        for (Button btn : new Button[]{btnProjetosOrcamento, btnProjetosCurso, btnLogout}) {
            btn.setStyle(estiloBtn);
        }

        menu.getChildren().addAll(nome, btnProjetosOrcamento, btnProjetosCurso, btnLogout);

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
        stage.setTitle("Painel do Especialista");
        stage.show();
    }
}
