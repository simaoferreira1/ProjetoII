package com.example.proj2.views;

import com.example.proj2.models.Projeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetalhesProjetosEmEsperaView {

    private final Projeto projeto;

    public DetalhesProjetosEmEsperaView(Projeto projeto) {
        this.projeto = projeto;
    }

    public void show() {
        Stage detalhesStage = new Stage();
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titulo = new Label("Detalhes do Projeto em Espera");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nomeProjeto = new Label("Nome: " + (projeto.getNome() != null ? projeto.getNome() : "Não definido"));
        Label descricao = new Label("Descrição: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "Não definida"));
        Label dataInicio = new Label("Data de Início: " + (projeto.getDatainicio() != null ? projeto.getDatainicio().toString() : "Não definida"));
        Label dataFimPrevista = new Label("Data Fim Prevista: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista().toString() : "Não definida"));
        Label estado = new Label("Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "Não definido"));
        Label localizacao = new Label("Localização: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "Não definida"));

        layout.getChildren().addAll(titulo, nomeProjeto, descricao, dataInicio, dataFimPrevista, estado, localizacao);

        Scene scene = new Scene(layout, 400, 450);
        detalhesStage.setScene(scene);
        detalhesStage.setTitle("Detalhes do Projeto em Espera");
        detalhesStage.show();
    }
}
