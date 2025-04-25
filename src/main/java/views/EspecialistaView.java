package com.example.proj2.views;

import com.example.proj2.models.Especialista;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EspecialistaView {

    private final Stage stage;
    private final Especialista especialista;

    public EspecialistaView(Stage stage, Especialista especialista) {
        this.stage = stage;
        this.especialista = especialista;
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Olá, " + especialista.getNome()));
        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Painel do Especialista");
        stage.show();
    }
}
