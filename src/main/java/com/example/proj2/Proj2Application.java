package com.example.proj2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class Proj2Application extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(Proj2Application.class)
                .web(WebApplicationType.NONE)  // 👈 esta linha impede o erro do servidor web
                .headless(false)
                .run();

        SpringContext.setApplicationContext(context); // 👈 agora está depois
    }

    @Override
    public void start(Stage primaryStage) {
        new views.LoginView().start(primaryStage); // 👈 mostrar tela de login
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


