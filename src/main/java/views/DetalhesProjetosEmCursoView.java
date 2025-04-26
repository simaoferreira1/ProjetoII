package com.example.proj2.views;

import com.example.proj2.models.Projeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetalhesProjetosEmCursoView {

    private final Projeto projeto;

    public DetalhesProjetosEmCursoView(Projeto projeto) {
        this.projeto = projeto;
    }

    public void show() {
        Stage detalhesStage = new Stage();

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-background-color: white;");

        Label titulo = new Label("📋 Detalhes do Projeto");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label lblNome = new Label("📌 Nome: " + (projeto.getNome() != null ? projeto.getNome() : "N/A"));
        Label lblDescricao = new Label("📝 Descri\u00e7\u00e3o: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A"));
        Label lblEstado = new Label("\u2699\uFE0F Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "N/A"));
        Label lblDataInicio = new Label("\uD83D\uDCC5 In\u00edcio: " + (projeto.getDatainicio() != null ? projeto.getDatainicio() : "N/A"));
        Label lblDataFimPrevista = new Label("\uD83D\uDCC5 Fim Previsto: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista() : "N/A"));
        Label lblLocalizacao = new Label("\uD83D\uDCCD Localiza\u00e7\u00e3o: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N/A"));

        VBox infoBox = new VBox(10);
        infoBox.getChildren().addAll(lblNome, lblDescricao, lblEstado, lblDataInicio, lblDataFimPrevista, lblLocalizacao);

        layout.getChildren().addAll(titulo, infoBox);

        Scene scene = new Scene(layout, 450, 350);
        detalhesStage.setScene(scene);
        detalhesStage.setTitle("Detalhes do Projeto");
        detalhesStage.show();
    }
}