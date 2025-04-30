package com.example.proj2.views;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Projeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DetalhesProjetosEmCursoView {

    private final Projeto projeto;
    private final Stage stage;
    private final Gestordeprojeto gestor;

    public DetalhesProjetosEmCursoView(Projeto projeto, Stage stage, Gestordeprojeto gestor) {
        this.projeto = projeto;
        this.stage = stage;
        this.gestor = gestor;
    }

    public void show() {
        Stage detalheStage = new Stage(); // Nova janela

        VBox conteudo = new VBox(15);
        conteudo.setPadding(new Insets(25));
        conteudo.setStyle("-fx-background-color: white;");
        conteudo.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("📋 Detalhes do Projeto");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        if (projeto == null) {
            Label mensagem = new Label("⚠️ Nenhum projeto disponível para mostrar.");
            mensagem.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            conteudo.getChildren().addAll(titulo, mensagem);
        } else {
            // Forçar o carregamento das propriedades relacionadas
            if (projeto.getIdcliente() != null) {
                projeto.getIdcliente().getNome(); // Forçar o carregamento do nome do cliente
            }
            if (projeto.getGestordeprojeto() != null) {
                projeto.getGestordeprojeto().getNome(); // Forçar o carregamento do nome do gestor
            }

            VBox infoBox = new VBox(8);
            infoBox.getChildren().addAll(
                    new Label("🆔 ID do Projeto: " + (projeto.getId() != null ? projeto.getId() : "N/A")),
                    new Label("📌 Nome: " + (projeto.getNome() != null ? projeto.getNome() : "N/A")),
                    new Label("📝 Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N/A")),
                    new Label("⚙️ Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "N/A")),
                    new Label("📅 Data de Início: " + (projeto.getDatainicio() != null ? projeto.getDatainicio() : "N/A")),
                    new Label("📆 Data Fim Prevista: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista() : "N/A")),
                    new Label("📍 Localização: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N/A")),
                    new Label("👤 Cliente: " + (projeto.getIdcliente() != null && projeto.getIdcliente().getNome() != null ? projeto.getIdcliente().getNome() : "N/A")),
                    new Label("🧑‍💼 Gestor: " + (projeto.getGestordeprojeto() != null && projeto.getGestordeprojeto().getNome() != null ? projeto.getGestordeprojeto().getNome() : "N/A"))
            );

            conteudo.getChildren().addAll(titulo, infoBox);
        }

        Scene scene = new Scene(conteudo, 420, 350);
        detalheStage.setScene(scene);
        detalheStage.setTitle("Detalhes do Projeto");
        detalheStage.initOwner(stage); // Liga à janela principal
        detalheStage.showAndWait();
    }
}