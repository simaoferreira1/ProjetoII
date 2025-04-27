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

    private final Stage stage;
    private final Solicitacaoprojeto solicitacao;

    public DetalhesSolicitacaoView(Stage parentStage, Solicitacaoprojeto solicitacao) {
        this.solicitacao = solicitacao;
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parentStage);
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        Label titulo = new Label("Detalhes da Solicitação");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Informações do cliente
        String nomeCliente = "N/A";
        String emailCliente = "N/A";
        String telefoneCliente = "N/A";
        try {
            nomeCliente = solicitacao.getCliente() != null && solicitacao.getCliente().getNome() != null
                    ? solicitacao.getCliente().getNome()
                    : "N/A";
            emailCliente = solicitacao.getCliente() != null && solicitacao.getCliente().getEmail() != null
                    ? solicitacao.getCliente().getEmail()
                    : "N/A";
            telefoneCliente = solicitacao.getCliente() != null && solicitacao.getCliente().getTelefone() != null
                    ? solicitacao.getCliente().getTelefone().toString() // Convertendo BigDecimal para String
                    : "N/A";
        } catch (Exception e) {
            System.out.println("Erro ao obter dados do cliente para solicitação: " + e.getMessage());
        }
        Label clienteLabel = new Label("Cliente: " + nomeCliente);
        Label emailLabel = new Label("Email: " + emailCliente);
        Label telefoneLabel = new Label("Telefone: " + telefoneCliente);

        // Informações da solicitação
        Label nomeSolicitacaoLabel = new Label("Nome da Solicitação: " + (solicitacao.getNome() != null ? solicitacao.getNome() : "N/A"));
        Label descricaoLabel = new Label("Descrição: " + (solicitacao.getDescricao() != null ? solicitacao.getDescricao() : "N/A"));
        Label dataLabel = new Label("Data da solicitação: " + (solicitacao.getDatasolicitacao() != null ? solicitacao.getDatasolicitacao() : "N/A"));
        Label estadoLabel = new Label("Estado: " + (solicitacao.getEstado() != null ? solicitacao.getEstado() : "N/A"));

        layout.getChildren().addAll(titulo, clienteLabel, emailLabel, telefoneLabel,
                nomeSolicitacaoLabel, descricaoLabel, dataLabel, estadoLabel);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Detalhes da Solicitação");
        stage.show();
    }
}