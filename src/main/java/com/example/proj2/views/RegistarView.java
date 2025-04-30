package com.example.proj2.views;

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

        TextField telefoneField = new TextField();
        telefoneField.setPromptText("Telefone");
        emailField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Palavra-passe");
        passwordField.setMaxWidth(250);

        ComboBox<String> tipoUtilizadorComboBox = new ComboBox<>();
        tipoUtilizadorComboBox.getItems().addAll(
                "Gestor de Projeto",
                "Especialista",
                "Membro Departamento Financeiro"
        );
        tipoUtilizadorComboBox.setPromptText("Tipo de Utilizador");
        tipoUtilizadorComboBox.setMaxWidth(250);

        Button registerButton = new Button("Registar");
        registerButton.setPrefWidth(250);
        registerButton.setStyle("-fx-background-color: #f5c242; -fx-text-fill: black; -fx-font-weight: bold;");

        HBox loginBox = new HBox(5);
        loginBox.setAlignment(Pos.CENTER);
        Label hasAccount = new Label("Já tem uma conta?");
        Hyperlink loginLink = new Hyperlink("Entrar");
        loginLink.setStyle("-fx-text-fill: #f5c242; -fx-font-weight: bold;");

        loginBox.getChildren().addAll(hasAccount, loginLink);

        // Ir para página de login quando clicar no link
        loginLink.setOnAction(e -> {
            new LoginView(stage).show();
        });

        // Lógica do botão registar
        registerButton.setOnAction(e -> {
            String nome = nameField.getText();
            String email = emailField.getText();
            String telefone = telefoneField.getText();
            String password = passwordField.getText();
            String tipoUtilizador = tipoUtilizadorComboBox.getValue();

            // Validação básica
            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || password.isEmpty() || tipoUtilizador == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, preencha todos os campos!");
                alert.showAndWait();
            } else {
                System.out.println("Nome: " + nome);
                System.out.println("Email: " + email);
                System.out.println("Telefone: " + telefone);
                System.out.println("Password: " + password);
                System.out.println("Tipo de Utilizador: " + tipoUtilizador);

                // Aqui você pode adicionar a lógica para salvar o utilizador no banco de dados
                // Por enquanto, após o registro bem-sucedido, voltamos para a tela de login
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Sucesso");
                success.setHeaderText(null);
                success.setContentText("Utilizador registado com sucesso!");
                success.showAndWait();

                new LoginView(stage).show();
            }
        });

        root.getChildren().addAll(
                logo, separator,
                nameField, emailField, telefoneField, passwordField, tipoUtilizadorComboBox,
                registerButton, loginBox
        );

        Scene scene = new Scene(root, 400, 650);
        stage.setScene(scene);
        stage.setTitle("Registar - Betonarte");
        stage.show();
    }
}