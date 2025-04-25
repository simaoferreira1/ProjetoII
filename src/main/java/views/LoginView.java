package views;

import com.example.proj2.controller.LoginController;
import com.example.proj2.SpringContext;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.scene.control.Alert;



public class LoginView {

    public void start(Stage stage) {
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");

        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            Task<Void> loginTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    LoginController controller = SpringContext.getBean(LoginController.class);
                    String result = controller.login(email, password, stage);

                    // Mostra o erro se houver
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

            new Thread(loginTask).start(); // <- corre o login em background
        });



        VBox root = new VBox(10, emailField, passwordField, loginBtn);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(300, 200);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}

