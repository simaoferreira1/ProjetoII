package com.example.proj2;

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
        // Mostra o menu de login ao invés de entrar direto como gestor
        new LoginView(primaryStage).show();
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
