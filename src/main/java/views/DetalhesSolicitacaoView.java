package com.example.proj2.views;

import com.example.proj2.models.Solicitacaoprojeto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        detalhesStage.setTitle("Detalhes da Solicitação");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);

        Label lblId = new Label("ID: " + solicitacao.getId());
        Label lblLocal = new Label("Local da reunião: " + solicitacao.getLocalreuniao());
        Label lblEstado = new Label("Estado: " + solicitacao.getEstado());
        Label lblData = new Label("Data da solicitação: " + solicitacao.getDatasolicitacao());

        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> detalhesStage.close());

        layout.getChildren().addAll(lblId, lblLocal, lblEstado, lblData, btnFechar);

        Scene scene = new Scene(layout, 400, 300);
        detalhesStage.setScene(scene);
        detalhesStage.showAndWait();
    }
}
