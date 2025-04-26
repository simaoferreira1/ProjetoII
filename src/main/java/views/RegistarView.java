package com.example.proj2.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class RegistarView {

    private Stage stage;

    public RegistarView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: white;");

        // Logo
        Image logoImage = new Image(getClass().getResource("/images/Capacete.png").toExternalForm());
        ImageView logo = new ImageView(logoImage);
        logo.setFitHeight(100);
        logo.setPreserveRatio(true);

        Region separator = new Region();
        separator.setPrefHeight(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Nome");
        nameField.setMaxWidth(250);

        TextField emailField = new TextField();
        emailField.setPromptText("E-mail");
        emailField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Palavra-passe");
        passwordField.setMaxWidth(250);

        Button registerButton = new Button("Registar");
        registerButton.setPrefWidth(250);
        registerButton.setStyle("-fx-background-color: #f5c242; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox loginBox = new HBox(5);
        loginBox.setAlignment(Pos.CENTER);
        Label hasAccount = new Label("Já tem uma conta?");
        Hyperlink loginLink = new Hyperlink("Entrar");
        loginLink.setStyle("-fx-text-fill: #f5c242; -fx-font-weight: bold;");

        loginBox.getChildren().addAll(hasAccount, loginLink);

        // Ir para página de login quando clicar no link
        loginLink.setOnAction(e -> {
            new com.example.proj2.views.LoginView(stage).show();
        });

        // TODO: Aqui vais depois pôr a lógica de registar no botão
        registerButton.setOnAction(e -> {
            // Exemplo: guardar dados no banco de dados
        });

        root.getChildren().addAll(logo, separator, nameField, emailField, passwordField, registerButton, loginBox);

        Scene scene = new Scene(root, 400, 550);
        stage.setScene(scene);
        stage.setTitle("Registar - Betonarte");
        stage.show();
    }
}
