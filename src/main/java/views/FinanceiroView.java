package com.example.proj2.views;

import com.example.proj2.models.Membrodepartamentofinanceiro;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FinanceiroView {

    private final Stage stage;
    private final Membrodepartamentofinanceiro financeiro;

    public FinanceiroView(Stage stage, Membrodepartamentofinanceiro financeiro) {
        this.stage = stage;
        this.financeiro = financeiro;
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Bem-vindo, " + financeiro.getNome()));
        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Painel do Financeiro");
        stage.show();
    }
}
