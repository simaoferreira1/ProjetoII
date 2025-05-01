package com.example.proj2.views;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Projeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetalhesProjetoPrePlaneamentoView {

    private final Projeto projeto;
    private final Stage parentStage;
    private final Gestordeprojeto gestor;

    public DetalhesProjetoPrePlaneamentoView(Projeto projeto, Stage parentStage, Gestordeprojeto gestor) {
        this.projeto = projeto;
        this.parentStage = parentStage;
        this.gestor = gestor;
    }

    public void show() {
        Stage detalhesStage = new Stage();
        detalhesStage.setTitle("Detalhes do Projeto (Pré-Planeamento)");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-background-color: white;");

        Label titulo = new Label("📋 Detalhes do Projeto");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label nomeProjeto = new Label("📌 Nome: " + (projeto.getNome() != null ? projeto.getNome() : "N/A"));
        Label descricao = new Label("📝 Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A"));
        Label estado = new Label("⚙️ Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "N/A"));
        Label dataInicio = new Label("📅 Data de Início: " + (projeto.getDatainicio() != null ? projeto.getDatainicio().toString() : "N/A"));
        Label dataFim = new Label("📅 Data de Fim Prevista: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista().toString() : "N/A"));
        Label localizacao = new Label("📍 Localização: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N/A"));

        VBox infoBox = new VBox(10);
        infoBox.getChildren().addAll(nomeProjeto, descricao, estado, dataInicio, dataFim, localizacao);

        layout.getChildren().addAll(titulo, infoBox);

        Scene scene = new Scene(layout, 450, 350);
        detalhesStage.setScene(scene);
        detalhesStage.initOwner(parentStage);
        detalhesStage.show();
    }
}
