package com.example.proj2.views;

import com.example.proj2.models.Projeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DetalhesProjetoCursoEspecialistaView {

    private final Projeto projeto;

    public DetalhesProjetoCursoEspecialistaView(Projeto projeto) {
        this.projeto = projeto;
    }

    public void show() {
        Stage detailsStage = new Stage();

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-background-color: white;");

        Label titulo = new Label("📋 Detalhes do Projeto");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label nome = new Label("📌 Nome: " + projeto.getNome());
        Label descricao = new Label("📝 Descrição: " + projeto.getDescricao());
        Label dataInicio = new Label("📅 Início: " + projeto.getDatainicio());
        Label dataFim = new Label("📅 Fim Previsto: " + projeto.getDatafimprevista());
        Label localizacao = new Label("📍 Localização: " + projeto.getLocalizacao());
        Label estado = new Label("• Estado: " + projeto.getEstado());


        VBox infoBox = new VBox(10);
        infoBox.getChildren().addAll(nome, descricao, dataInicio, dataFim, localizacao, estado);

        layout.getChildren().addAll(titulo, infoBox);

        Scene scene = new Scene(layout, 450, 350);
        detailsStage.setScene(scene);
        detailsStage.setTitle("Detalhes do Projeto");
        detailsStage.show();
    }
}
