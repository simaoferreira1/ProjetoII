package com.example.proj2.views;

import com.example.proj2.models.Solicitacaoprojeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetalhesSolicitacaoView {

    private final Stage parentStage;
    private final Solicitacaoprojeto solicitacao;

    public DetalhesSolicitacaoView(Stage parentStage, Solicitacaoprojeto solicitacao) {
        this.parentStage = parentStage;
        this.solicitacao = solicitacao;
    }

    public void show() {
        Stage detalhesStage = new Stage();
        detalhesStage.initOwner(parentStage);
        detalhesStage.initModality(Modality.APPLICATION_MODAL);
        detalhesStage.setTitle("Detalhes da Solicita\u00e7\u00e3o");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-background-color: white;");

        Label titulo = new Label("\uD83D\uDCCB Detalhes da Solicita\u00e7\u00e3o");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label lblId = new Label("\uD83D\uDDC2 ID: " + (solicitacao.getId() != null ? solicitacao.getId() : "N\u00e3o definido"));
        Label lblLocal = new Label("\uD83D\uDCCD Local da reuni\u00e3o: " + (solicitacao.getLocalreuniao() != null ? solicitacao.getLocalreuniao() : "N\u00e3o definido"));
        Label lblEstado = new Label("\u2699\uFE0F Estado: " + (solicitacao.getEstado() != null ? solicitacao.getEstado() : "N\u00e3o definido"));
        Label lblData = new Label("\uD83D\uDCC5 Data da solicita\u00e7\u00e3o: " + (solicitacao.getDatasolicitacao() != null ? solicitacao.getDatasolicitacao().toString() : "N\u00e3o definida"));

        VBox infoBox = new VBox(10);
        infoBox.getChildren().addAll(lblId, lblLocal, lblEstado, lblData);

        layout.getChildren().addAll(titulo, infoBox);

        Scene scene = new Scene(layout, 450, 350);
        detalhesStage.setScene(scene);
        detalhesStage.showAndWait();
    }
}