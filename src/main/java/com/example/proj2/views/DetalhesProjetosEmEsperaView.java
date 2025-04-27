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

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-background-color: white;");

        Label titulo = new Label("📋 Detalhes do Projeto em Espera");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label nomeProjeto = new Label("📌 Nome: " + (projeto.getNome() != null ? projeto.getNome() : "N\u00e3o definido"));
        Label descricao = new Label("📝 Descri\u00e7\u00e3o: " + (projeto.getDescricao() != null ? projeto.getDescricao() : "N\u00e3o definida"));
        Label dataInicio = new Label("\uD83D\uDCC5 In\u00edcio: " + (projeto.getDatainicio() != null ? projeto.getDatainicio().toString() : "N\u00e3o definida"));
        Label dataFimPrevista = new Label("\uD83D\uDCC5 Fim Previsto: " + (projeto.getDatafimprevista() != null ? projeto.getDatafimprevista().toString() : "N\u00e3o definida"));
        Label estado = new Label("\u2699\uFE0F Estado: " + (projeto.getEstado() != null ? projeto.getEstado() : "N\u00e3o definido"));
        Label localizacao = new Label("\uD83D\uDCCD Localiza\u00e7\u00e3o: " + (projeto.getLocalizacao() != null ? projeto.getLocalizacao() : "N\u00e3o definida"));

        VBox infoBox = new VBox(10);
        infoBox.getChildren().addAll(nomeProjeto, descricao, dataInicio, dataFimPrevista, estado, localizacao);

        layout.getChildren().addAll(titulo, infoBox);

        Scene scene = new Scene(layout, 450, 350);
        detalhesStage.setScene(scene);
        detalhesStage.setTitle("Detalhes do Projeto em Espera");
        detalhesStage.show();
    }
}