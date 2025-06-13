package com.example.proj2.views;

import com.example.proj2.controller.desktop.LoginController;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.services.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class LoginView {

    private final Stage stage;
    private final LoginController loginController;

    public LoginView(Stage stage, LoginController loginController) {
        this.stage = stage;
        this.loginController = loginController;
    }

    public void show() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: white;");

        // Logo
        Image logoImage = new Image(getClass().getResource("/images/Capacete.png").toExternalForm());
        ImageView logo = new ImageView(logoImage);
        logo.setFitWidth(150);
        logo.setPreserveRatio(true);

        Region separator = new Region();
        separator.setPrefHeight(10);

        TextField emailField = new TextField();
        emailField.setPromptText("E-mail");
        emailField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Palavra-passe");
        passwordField.setMaxWidth(250);

        Button loginButton = new Button("Entrar");
        loginButton.setPrefWidth(250);
        loginButton.setStyle("-fx-background-color: #f5c242; -fx-text-fill: white; -fx-font-weight: bold;");

        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert("Erro", "Por favor, preencha todos os campos.");
                return;
            }

            String resultado = loginController.login(email, password, stage);
            if (resultado != null) {
                showAlert("Erro", resultado);
            }
        });

        HBox registerBox = new HBox(5);
        registerBox.setAlignment(Pos.CENTER);
        Label noAccount = new Label("Não tem uma conta?");
        Hyperlink registerLink = new Hyperlink("Registar");
        registerLink.setStyle("-fx-text-fill: #f5c242; -fx-font-weight: bold;");
        registerLink.setOnAction(e -> {
            new RegistarView(stage, loginController).show();
        });

        registerBox.getChildren().addAll(noAccount, registerLink);

        root.getChildren().addAll(logo, separator, emailField, passwordField, loginButton, registerBox);

        Scene scene = new Scene(root, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Login - Betonarte");
        stage.show();
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
