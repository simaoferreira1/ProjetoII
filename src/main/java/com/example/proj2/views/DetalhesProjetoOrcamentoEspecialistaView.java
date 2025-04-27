package com.example.proj2.views;

import com.example.proj2.models.Orcamentoprojeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DetalhesProjetoOrcamentoEspecialistaView {

    private final Orcamentoprojeto orcamento;

    public DetalhesProjetoOrcamentoEspecialistaView(Orcamentoprojeto orcamento) {
        this.orcamento = orcamento;
    }

    public void show() {
        Stage detalheStage = new Stage();

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: white;");


        // === Centro - Detalhes ===
        VBox conteudo = new VBox(20);
        conteudo.setAlignment(Pos.TOP_LEFT);
        conteudo.setPadding(new Insets(30));

        Label titulo = new Label("📋 Detalhes do Projeto para Orçamento");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label nomeProjeto = new Label("📌 Projeto: " + orcamento.getProjeto().getNome());
        Label estado = new Label("• Estado: " + orcamento.getEstado());
        Label valor = new Label("💰 Valor Total: " + orcamento.getValortotal() + " €");
        Label dataAprovacao = new Label("📅 Data de Aprovação: " +
                (orcamento.getDataaprovacao() != null ? orcamento.getDataaprovacao().toString() : "Não aprovada"));


        VBox infoBox = new VBox(10);
        infoBox.getChildren().addAll(nomeProjeto, estado, valor, dataAprovacao);

        conteudo.getChildren().addAll(titulo, infoBox);

        layout.setCenter(conteudo);

        Scene scene = new Scene(layout, 500, 400);
        detalheStage.setScene(scene);
        detalheStage.setTitle("Detalhes do Projeto para Orçamento");
        detalheStage.show();
    }
}
