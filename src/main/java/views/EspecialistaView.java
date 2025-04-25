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
import javafx.stage.Stage;

public class EspecialistaView {

    private final Stage stage;
    private final Especialista especialista;

    public EspecialistaView(Stage stage, Especialista especialista) {
        this.stage = stage;
        this.especialista = especialista;
    }

    public void show() {
        BorderPane layout = new BorderPane();

        // === MENU LATERAL ===
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Especialista");
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: white; -fx-text-fill: #333333; -fx-font-size: 14px; -fx-pref-width: 180px; -fx-pref-height: 80px;";

        Button btnProjetosCurso = new Button("📂 Projetos\nem curso");
        btnProjetosCurso.setStyle(estiloBtn);
        btnProjetosCurso.setOnAction(e -> new ProjetosCursoEspecialistaView(stage).show());

        Button btnProjetosOrcamento = new Button("💰 Projetos\npara orçamento");
        btnProjetosOrcamento.setStyle(estiloBtn);
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoEspecialistaView(stage).show());

        Button btnLogout = new Button("↩ Log Out");
        btnLogout.setStyle(estiloBtn);
        btnLogout.setOnAction(e -> stage.close());

        menu.getChildren().addAll(nome, btnProjetosCurso, btnProjetosOrcamento, btnLogout);
        layout.setLeft(menu);

        // === CONTEÚDO CENTRAL COM IMAGEM DE FUNDO ===
        StackPane centerPane = new StackPane();
        ImageView backgroundImage = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setFitWidth(450); // ajustável
        backgroundImage.setOpacity(0.2); // transparência
        centerPane.getChildren().add(backgroundImage);
        centerPane.setAlignment(Pos.CENTER);
        layout.setCenter(centerPane);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Painel do Especialista");
        stage.show();
    }
}
