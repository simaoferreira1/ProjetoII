package com.example.proj2.views;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Projeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DetalhesProjetosEmCursoView {

    private final Projeto projeto;
    private final Stage stage;
    private final Gestordeprojeto gestor;

    public DetalhesProjetosEmCursoView(Projeto projeto, Stage stage, Gestordeprojeto gestor) {
        this.projeto = projeto;
        this.stage = stage;
        this.gestor = gestor;
    }

    public void show() {
        Stage detalheStage = new Stage(); // Nova janela

        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(25));
        conteudo.setStyle("-fx-background-color: white;");
        conteudo.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("📋 Detalhes do Projeto");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox infoBox = new VBox(8);
        infoBox.getChildren().addAll(
                new Label("📌 Nome: " + (projeto.getNome() != null ? projeto.getNome() : "N/A")),
                new Label("📝 Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A")),
                new Label("⚙️ Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "N/A")),
                new Label("📅 Data de Início: " + (projeto.getDatainicio() != null ? projeto.getDatainicio() : "N/A")),
                new Label("📆 Data Fim Prevista: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista() : "N/A")),
                new Label("📍 Localização: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N/A")),
                new Label("👤 Cliente: " + (projeto.getIdcliente() != null && projeto.getIdcliente().getNome() != null
                        ? projeto.getIdcliente().getNome() : "N/A"))
        );


        conteudo.getChildren().addAll(titulo, infoBox);

        Scene scene = new Scene(conteudo, 420, 350);
        detalheStage.setScene(scene);
        detalheStage.setTitle("Detalhes do Projeto");
        detalheStage.initOwner(stage); // liga à janela principal
        detalheStage.show();
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