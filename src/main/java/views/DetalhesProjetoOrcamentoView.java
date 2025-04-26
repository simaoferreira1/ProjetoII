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
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titulo = new Label("Detalhes do Projeto para Orçamento");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nomeProjeto = new Label("Projeto: " + (orcamentoprojeto.getProjeto() != null ? orcamentoprojeto.getProjeto().getNome() : "Não definido"));
        Label valorTotal = new Label("Valor Total: " + (orcamentoprojeto.getValortotal() != null ? orcamentoprojeto.getValortotal() + " €" : "Não definido"));
        Label dataAprovacao = new Label("Data de Aprovação: " + (orcamentoprojeto.getDataaprovacao() != null ? orcamentoprojeto.getDataaprovacao().toString() : "Não definida"));
        Label estado = new Label("Estado: " + (orcamentoprojeto.getEstado() != null ? orcamentoprojeto.getEstado() : "Não definido"));

        layout.getChildren().addAll(titulo, nomeProjeto, valorTotal, dataAprovacao, estado);

        Scene scene = new Scene(layout, 400, 400);
        detalhesStage.setScene(scene);
        detalhesStage.setTitle("Detalhes do Orçamento");
        detalhesStage.show();
    }
}
