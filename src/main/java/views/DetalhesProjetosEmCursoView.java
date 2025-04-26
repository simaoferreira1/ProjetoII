package com.example.proj2.views;

import com.example.proj2.models.Projeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetalhesProjetosEmCursoView {

    private final Projeto projeto;

    public DetalhesProjetosEmCursoView(Projeto projeto) {
        this.projeto = projeto;
    }

    public void show() {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();

        // === TOPO COM LOGO ===
        VBox topo = new VBox();
        topo.setAlignment(Pos.TOP_RIGHT);
        topo.setPadding(new Insets(10));

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);
        topo.getChildren().add(logo);
        root.setTop(topo);

        // === CONTEÚDO CENTRAL ===
        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(20));
        conteudo.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Detalhes do Projeto");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblNome = new Label("Nome: " + projeto.getNome());
        Label lblDescricao = new Label("Descrição: " + projeto.getDescricao());
        Label lblEstado = new Label("Estado: " + projeto.getEstado());
        Label lblDataInicio = new Label("Data de Início: " + (projeto.getDatainicio() != null ? projeto.getDatainicio() : "N/A"));
        Label lblDataFimPrevista = new Label("Data Fim Prevista: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista() : "N/A"));
        Label lblLocalizacao = new Label("Localização: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N/A"));

        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> stage.close());

        conteudo.getChildren().addAll(titulo, lblNome, lblDescricao, lblEstado, lblDataInicio, lblDataFimPrevista, lblLocalizacao, btnFechar);
        root.setCenter(conteudo);

        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Detalhes do Projeto");
        stage.show();
    }
}
