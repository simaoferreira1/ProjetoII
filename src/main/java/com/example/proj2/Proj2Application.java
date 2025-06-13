package com.example.proj2;

import com.example.proj2.controller.desktop.LoginController;
import com.example.proj2.views.LoginView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "com.example.proj2")
public class Proj2Application extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(Proj2Application.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run();

        SpringContext.setApplicationContext(context);
    }

    @Override
    public void start(Stage primaryStage) {
        // Obtém o LoginController do contexto do Spring
        LoginController loginController = context.getBean(LoginController.class);
        new LoginView(primaryStage, loginController).show();
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
