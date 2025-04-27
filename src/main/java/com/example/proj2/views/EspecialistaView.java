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

        // Estilos de Botões
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

        // Botões
        Button btnProjetosCurso = criarBotao("📂 Projetos\nem curso", estiloBtn, estiloHover);
        btnProjetosCurso.setOnAction(e -> new ProjetosCursoEspecialistaView(stage).show());

        Button btnProjetosOrcamento = criarBotao("💰 Projetos\npara orçamento", estiloBtn, estiloHover);
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoEspecialistaView(stage).show());

        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);
        btnLogout.setOnAction(e -> stage.close());

        conteudoMenu.getChildren().addAll(nome, btnProjetosCurso, btnProjetosOrcamento);

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

    private Button criarBotao(String texto, String estiloBase, String estiloHover) {
        Button button = new Button(texto);
        button.setWrapText(true);
        button.setStyle(estiloBase);
        button.setOnMouseEntered(e -> button.setStyle(estiloBase + estiloHover));
        button.setOnMouseExited(e -> button.setStyle(estiloBase));
        return button;
    }
}
