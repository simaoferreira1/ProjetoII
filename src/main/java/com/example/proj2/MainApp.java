package com.example.proj2;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
@SpringBootApplication(scanBasePackages = "com.example.proj2")
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 👉 Inicializar o contexto Spring Boot
        ApplicationContext context = new SpringApplicationBuilder(Proj2Application.class)
                .run();

        // 👉 Guardar no SpringContext
        SpringContext.setApplicationContext(context);

        // 👉 Agora sim: abrir a primeira janela (Login, por exemplo)
        new com.example.proj2.views.LoginView(primaryStage).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
