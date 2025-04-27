package com.example.proj2.views;

import com.example.proj2.models.Orcamentoprojeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetalhesProjetoOrcamentoView {

    private final Orcamentoprojeto orcamentoprojeto;

    public DetalhesProjetoOrcamentoView(Orcamentoprojeto orcamentoprojeto) {
        this.orcamentoprojeto = orcamentoprojeto;
    }

    public void show() {
        Stage detalhesStage = new Stage();

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-background-color: white;");

        Label titulo = new Label("📋 Detalhes do Projeto para Orçamento");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label nomeProjeto = new Label("📌 Projeto: " + (orcamentoprojeto.getProjeto() != null ? orcamentoprojeto.getProjeto().getNome() : "N\u00e3o definido"));
        Label valorTotal = new Label("\uD83D\uDCB0 Valor Total: " + (orcamentoprojeto.getValortotal() != null ? orcamentoprojeto.getValortotal() + " €" : "N\u00e3o definido"));
        Label dataAprovacao = new Label("\uD83D\uDCC5 Data de Aprova\u00e7\u00e3o: " + (orcamentoprojeto.getDataaprovacao() != null ? orcamentoprojeto.getDataaprovacao().toString() : "N\u00e3o definida"));
        Label estado = new Label("\u2699\uFE0F Estado: " + (orcamentoprojeto.getEstado() != null ? orcamentoprojeto.getEstado() : "N\u00e3o definido"));

        VBox infoBox = new VBox(10);
        infoBox.getChildren().addAll(nomeProjeto, valorTotal, dataAprovacao, estado);

        layout.getChildren().addAll(titulo, infoBox);

        Scene scene = new Scene(layout, 450, 350);
        detalhesStage.setScene(scene);
        detalhesStage.setTitle("Detalhes do Or\u00e7amento");
        detalhesStage.show();
    }
}