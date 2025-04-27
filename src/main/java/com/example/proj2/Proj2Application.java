package com.example.proj2;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.services.GestordeprojetoService;
import com.example.proj2.views.GestorView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        GestordeprojetoService gestorService = context.getBean(GestordeprojetoService.class);
        Gestordeprojeto gestor = gestorService.listarGestores().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum gestor de projeto encontrado no banco de dados"));

        new GestorView(primaryStage, gestor).show();
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