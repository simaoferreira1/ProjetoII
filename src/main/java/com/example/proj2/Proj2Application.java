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
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.views.GestorView;
import com.example.proj2.models.Especialista;
import com.example.proj2.views.EspecialistaView;
import com.example.proj2.views.SolicitacoesView;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.proj2.views.ProjetosEmCursoView;
import com.example.proj2.views.ProjetosOrcamentoView;
import com.example.proj2.views.ProjetosEmEsperaView;




@SpringBootApplication(scanBasePackages = "com.example.proj2")
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
        Especialista especialistaFalso = new Especialista();
        especialistaFalso.setNome("Especialista Exemplo"); // ou puxa da base de dados se quiseres
        new EspecialistaView(primaryStage, especialistaFalso).show();
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


