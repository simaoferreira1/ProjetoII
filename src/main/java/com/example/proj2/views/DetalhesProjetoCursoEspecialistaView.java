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

        layout.getChildren().add(titulo);

        if (projeto == null) {
            Label mensagem = new Label("⚠️ Nenhum projeto em curso disponível para mostrar.");
            mensagem.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");
            layout.getChildren().add(mensagem);
        } else {
            // Adicionando verificações de null para evitar NullPointerException
            Label nome = new Label("📌 Nome: " + (projeto.getNome() != null ? projeto.getNome() : "N/A"));
            Label descricao = new Label("📝 Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A"));
            Label dataInicio = new Label("📅 Início: " + (projeto.getDatainicio() != null ? projeto.getDatainicio() : "N/A"));
            Label dataFim = new Label("📅 Fim Previsto: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista() : "N/A"));
            Label localizacao = new Label("📍 Localização: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N/A"));
            Label estado = new Label("• Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "N/A"));


            VBox infoBox = new VBox(10);
            infoBox.getChildren().addAll(nome, descricao, dataInicio, dataFim, localizacao, estado);

            layout.getChildren().add(infoBox);
        }

        Scene scene = new Scene(layout, 450, 350);
        detailsStage.setScene(scene);
        detailsStage.setTitle("Detalhes do Projeto");
        detailsStage.show();
    }
}