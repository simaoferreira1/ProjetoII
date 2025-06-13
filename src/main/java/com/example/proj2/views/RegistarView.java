package com.example.proj2.views;

import com.example.proj2.controller.desktop.LoginController;
import com.example.proj2.models.Tipoespecialista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.Arrays;
import java.util.List;

public class RegistarView {
    private final Stage stage;
    private final LoginController loginController;

    public RegistarView(Stage stage, LoginController loginController) {
        this.stage = stage;
        this.loginController = loginController;
    }

    public void show() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: white;");

        // Logo (apenas o logo, sem texto em baixo)
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
        telefoneField.setMaxWidth(250);

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

        // ComboBox para Tipo de Especialista (inicialmente escondido)
        ComboBox<Tipoespecialista> tipoEspecialistaComboBox = new ComboBox<>();
        tipoEspecialistaComboBox.setPromptText("Tipo de Especialista");
        tipoEspecialistaComboBox.setMaxWidth(250);
        tipoEspecialistaComboBox.setVisible(false);

        // Simulação: lista de tipos de especialista (substitua por busca real)
        List<Tipoespecialista> tipos = Arrays.asList(
                criarTipo(1, "Engenheiro Civil"),
                criarTipo(2, "Arquiteto"),
                criarTipo(3, "Eletricista")
        );
        ObservableList<Tipoespecialista> listaTipos = FXCollections.observableArrayList(tipos);
        tipoEspecialistaComboBox.setItems(listaTipos);

        // Mostrar apenas a descrição no ComboBox
        tipoEspecialistaComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Tipoespecialista item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDescricao());
            }
        });
        tipoEspecialistaComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Tipoespecialista item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDescricao());
            }
        });

        // Mostra/esconde o campo tipo de especialista conforme a seleção
        tipoUtilizadorComboBox.setOnAction(e -> {
            String tipo = tipoUtilizadorComboBox.getValue();
            tipoEspecialistaComboBox.setVisible("Especialista".equals(tipo));
        });

        Button registerButton = new Button("Registar");
        registerButton.setPrefWidth(250); // igual ao botão de login
        registerButton.setStyle("-fx-background-color: #f5c242; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox loginBox = new HBox(5);
        loginBox.setAlignment(Pos.CENTER);
        Label hasAccount = new Label("Já tem uma conta?");
        hasAccount.setStyle("-fx-font-size: 12px;");
        Hyperlink loginLink = new Hyperlink("Entrar");
        loginLink.setStyle("-fx-text-fill: #f5c242; -fx-font-weight: bold; -fx-font-size: 12px;");

        loginBox.getChildren().addAll(hasAccount, loginLink);

        // Ir para página de login quando clicar no link
        loginLink.setOnAction(e -> new LoginView(stage, loginController).show());

        // Lógica do botão registar
        registerButton.setOnAction(e -> {
            String nome = nameField.getText();
            String email = emailField.getText();
            String telefone = telefoneField.getText();
            String password = passwordField.getText();
            String tipoUtilizador = tipoUtilizadorComboBox.getValue();

            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || password.isEmpty() || tipoUtilizador == null) {
                showAlert("Erro", "Por favor, preencha todos os campos!");
                return;
            }
            if ("Especialista".equals(tipoUtilizador)) {
                Tipoespecialista tipoEspecialistaSelecionado = tipoEspecialistaComboBox.getValue();
                if (tipoEspecialistaSelecionado == null) {
                    showAlert("Erro", "Por favor, selecione o tipo de especialista!");
                    return;
                }
                // Aqui você pode passar o tipoEspecialistaSelecionado para o controller, se necessário
            }

            String resultado = loginController.registrar(nome, email, telefone, password, tipoUtilizador);

            if ("Utilizador registado com sucesso!".equals(resultado)) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Sucesso");
                success.setHeaderText(null);
                success.setContentText(resultado);
                success.showAndWait();

                new LoginView(stage, loginController).show();
            } else {
                showAlert("Erro", resultado);
            }
        });

        root.getChildren().addAll(
                logo, separator,
                nameField, emailField, telefoneField, passwordField, tipoUtilizadorComboBox,
                tipoEspecialistaComboBox, // campo extra
                registerButton, loginBox
        );

        Scene scene = new Scene(root, 400, 650);
        stage.setScene(scene);
        stage.setTitle("Registar - Betonarte");
        stage.show();
    }

    private Tipoespecialista criarTipo(int id, String descricao) {
        Tipoespecialista tipo = new Tipoespecialista();
        tipo.setId(id);
        tipo.setDescricao(descricao);
        return tipo;
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
