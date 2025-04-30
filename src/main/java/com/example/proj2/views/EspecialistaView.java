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

        // Exibir o nome do especialista no formato "👤 Especialista: Nome do Especialista"
        String nomeEspecialista = "Especialista Desconhecido";
        if (especialista != null && especialista.getNome() != null && !especialista.getNome().trim().isEmpty()) {
            nomeEspecialista = especialista.getNome();
        } else {
            // Log para depuração
            System.out.println("Especialista ou nome está null: " + (especialista == null ? "especialista é null" : "nome é null ou vazio"));
        }
        Label nome = new Label("👤 Especialista: " + nomeEspecialista);
        nome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        nome.setWrapText(true);
        nome.setAlignment(Pos.CENTER);

        // Envolver o Label em um HBox para centralizar melhor
        HBox nomeContainer = new HBox(nome);
        nomeContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(nome, Priority.ALWAYS);
        nome.setMaxWidth(Double.MAX_VALUE);

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
        Button btnProjetosCurso = criarBotao("📂 Projetos\nem Curso", estiloBtn, estiloHover);
        btnProjetosCurso.setOnAction(e -> new ProjetosCursoEspecialistaView(stage, especialista).show());

        Button btnProjetosOrcamento = criarBotao("💰 Projetos\nem Pré-Planeamento", estiloBtn, estiloHover);
        btnProjetosOrcamento.setOnAction(e -> new ProjetosOrcamentoEspecialistaView(stage, especialista).show());

        Button btnLogout = criarBotao("↩ Sair", estiloBtn, estiloHover);
        btnLogout.setOnAction(e -> stage.close());

        conteudoMenu.getChildren().addAll(nomeContainer, btnProjetosCurso, btnProjetosOrcamento);

        Region espacoInferior = new Region();
        VBox.setVgrow(espacoInferior, Priority.ALWAYS);

        menu.getChildren().addAll(conteudoMenu, espacoInferior, btnLogout);
        layout.setLeft(menu);

        // === CONTEÚDO CENTRAL COM IMAGEM DE FUNDO ===
        StackPane centerPane = new StackPane();
        try {
            ImageView backgroundImage = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
            backgroundImage.setPreserveRatio(true);
            backgroundImage.setFitWidth(450); // Ajustável
            backgroundImage.setOpacity(0.2); // Transparência
            centerPane.getChildren().add(backgroundImage);
        } catch (Exception e) {
            Label mensagemErro = new Label("⚠️ Não foi possível carregar a imagem de fundo.");
            mensagemErro.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            centerPane.getChildren().add(mensagemErro);
        }
        centerPane.setAlignment(Pos.CENTER);
        layout.setCenter(centerPane);

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Painel do Especialista");
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
}