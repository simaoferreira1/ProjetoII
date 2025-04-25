package com.example.proj2.views;

import com.example.proj2.models.Orcamentoprojeto;
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

public class DetalhesProjetoOrcamentoEspecialistaView {

    private final Orcamentoprojeto orcamento;

    public DetalhesProjetoOrcamentoEspecialistaView(Orcamentoprojeto orcamento) {
        this.orcamento = orcamento;
    }

    public void show() {
        Stage detalheStage = new Stage();

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // Topo - Logo
        VBox topoDireita = new VBox();
        topoDireita.setAlignment(Pos.TOP_RIGHT);
        topoDireita.setPadding(new Insets(10, 20, 0, 0));
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);
        topoDireita.getChildren().add(logo);
        layout.setTop(topoDireita);

        // Centro - Detalhes
        VBox conteudo = new VBox(15);
        conteudo.setAlignment(Pos.CENTER_LEFT);
        conteudo.setPadding(new Insets(20));

        Label titulo = new Label("Detalhes do Projeto para Orçamento");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nomeProjeto = new Label("Projeto: " + orcamento.getProjeto().getNome());
        Label estado = new Label("Estado: " + orcamento.getEstado());
        Label valor = new Label("Valor Total: " + orcamento.getValortotal() + " €");
        Label dataAprovacao = new Label("Data de Aprovação: " + (orcamento.getDataaprovacao() != null ? orcamento.getDataaprovacao().toString() : "Não aprovada"));

        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> detalheStage.close());

        conteudo.getChildren().addAll(titulo, nomeProjeto, estado, valor, dataAprovacao, btnFechar);

        layout.setCenter(conteudo);

        Scene scene = new Scene(layout, 600, 400);
        detalheStage.setScene(scene);
        detalheStage.setTitle("Detalhes do Projeto para Orçamento");
        detalheStage.show();
    }
}
