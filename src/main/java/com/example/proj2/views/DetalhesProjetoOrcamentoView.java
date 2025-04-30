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

        if (orcamentoprojeto == null) {
            Label mensagem = new Label("⚠️ Nenhum orçamento disponível para mostrar.");
            mensagem.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");
            layout.getChildren().addAll(titulo, mensagem);
        } else {
            // Adicionando verificações de null e exibindo IDs
            Label idOrcamento = new Label("🆔 ID do Orçamento: " + (orcamentoprojeto.getId() != null ? orcamentoprojeto.getId() : "N/A"));
            Label idProjeto = new Label("🆔 ID do Projeto: " + (orcamentoprojeto.getProjeto() != null && orcamentoprojeto.getProjeto().getId() != null ? orcamentoprojeto.getProjeto().getId() : "N/A"));
            Label nomeProjeto = new Label("📌 Projeto: " + (orcamentoprojeto.getProjeto() != null && orcamentoprojeto.getProjeto().getNome() != null ? orcamentoprojeto.getProjeto().getNome() : "Não definido"));
            Label valorTotal = new Label("💰 Valor Total: " + (orcamentoprojeto.getValortotal() != null ? orcamentoprojeto.getValortotal() + " €" : "Não definido"));
            Label dataAprovacao = new Label("📅 Data de Aprovação: " + (orcamentoprojeto.getDataaprovacao() != null ? orcamentoprojeto.getDataaprovacao().toString() : "Não definida"));
            Label estado = new Label("⚙️ Estado: " + (orcamentoprojeto.getEstado() != null ? orcamentoprojeto.getEstado() : "Não definido"));

            VBox infoBox = new VBox(10);
            infoBox.getChildren().addAll(idOrcamento, idProjeto, nomeProjeto, valorTotal, dataAprovacao, estado);

            layout.getChildren().addAll(titulo, infoBox);
        }

        Scene scene = new Scene(layout, 450, 350);
        detalhesStage.setScene(scene);
        detalhesStage.setTitle("Detalhes do Orçamento");
        detalhesStage.show();
    }
}