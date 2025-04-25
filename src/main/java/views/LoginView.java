package views;

import com.example.proj2.SpringContext;
import com.example.proj2.controller.LoginController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView {

    public void start(Stage stage) {
        // LOGO
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/Capacete.png").toExternalForm()));
        logo.setFitHeight(120);
        logo.setPreserveRatio(true);

        // TÍTULO
        Label titulo = new Label("BETONARTE");
        titulo.setFont(new Font("Arial", 28));
        titulo.setStyle("-fx-font-weight: bold;");

        Label subtitulo = new Label("Construção Civil");
        subtitulo.setFont(new Font("Arial", 14));
        subtitulo.setStyle("-fx-text-fill: #333;");

        VBox tituloBox = new VBox(titulo, subtitulo);
        tituloBox.setAlignment(Pos.CENTER);

        // CAMPOS
        TextField emailField = new TextField();
        emailField.setPromptText("E-mail");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Palavra-passe");

        Button loginBtn = new Button("Entrar");
        loginBtn.setStyle("-fx-background-color: #f1c40f; -fx-text-fill: black; -fx-font-weight: bold;");

        Label registerLink = new Label("Não tem uma conta? Registar");
        registerLink.setStyle("-fx-text-fill: #f1c40f; -fx-cursor: hand;");

        VBox formBox = new VBox(10, emailField, passwordField, loginBtn, registerLink);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(10));
        formBox.setMaxWidth(300);

        // LOGIN ACTION
        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            Task<Void> loginTask = new Task<>() {
                @Override
                protected Void call() {
                    LoginController controller = SpringContext.getBean(LoginController.class);
                    String result = controller.login(email, password, stage);

                    if (result != null) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erro de Login");
                            alert.setHeaderText(null);
                            alert.setContentText(result);
                            alert.showAndWait();
                        });
                    }
                    return null;
                }
            };

            new Thread(loginTask).start();
        });

        // ROOT
        VBox root = new VBox(15, logo, tituloBox, formBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));

        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}

