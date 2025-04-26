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
        layout.setStyle("-fx-background-color: white;");

        // === MENU LATERAL ===
        VBox menu = new VBox();
        menu.setStyle("-fx-background-color: #f1c40f;");
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setAlignment(Pos.TOP_CENTER);

        VBox conteudoMenu = new VBox(20);
        conteudoMenu.setAlignment(Pos.TOP_CENTER);

        Label nome = new Label("👤 Especialista");
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        String estiloBtn = "-fx-background-color: white; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 14px; " +
                "-fx-pref-width: 160px; " +
                "-fx-pref-height: 60px; " +
                "-fx-alignment: center;";

        Button btnProjetosCurso = new Button("📂 Projetos\nem curso");
        btnProjetosCurso.setStyle(estiloBtn);
        btnProjetosCurso.setOnAction(e -> new ProjetosCursoEspecialistaView(stage).show());

        Button btnProjetosOrcamento = new Button("💰 Projetos\npara orçamento");
        btnProjetosOrcamento.setStyle(estiloBtn);
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoEspecialistaView(stage).show());

        conteudoMenu.getChildren().addAll(nome, btnProjetosCurso, btnProjetosOrcamento);

        Button btnLogout = new Button("↩ Sair");
        btnLogout.setStyle(estiloBtn);
        btnLogout.setOnAction(e -> stage.close());

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);
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
