package com.example.proj2.views;

import com.example.proj2.models.Utilizador;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.proj2.models.Gestordeprojeto;


public class GestorView {
    private final Stage stage;
    private final Gestordeprojeto gestor;

    public GestorView(Stage stage, Gestordeprojeto gestor) {
        this.stage = stage;
        this.gestor = gestor;
    }

    public void show() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label label = new Label("Olá " + gestor.getNome());
        layout.getChildren().add(label);

        Scene scene = new Scene(layout, 600, 400);
        stage.setTitle("Painel do Gestor");
        stage.setScene(scene);
        stage.show();

    }
}
