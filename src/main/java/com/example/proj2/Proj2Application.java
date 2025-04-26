package com.example.proj2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.services.MembrodepartamentofinanceiroService;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.views.GestorView;
import com.example.proj2.views.ProjetosEmCursoFinanceiroView;
import com.example.proj2.views.LoginView;
import com.example.proj2.views.FinanceiroView;
import com.example.proj2.models.Especialista;
import com.example.proj2.views.EspecialistaView;


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

    /*@Override
    public void start(Stage primaryStage) {
        Especialista especialistaFalso = new Especialista();
        especialistaFalso.setNome("Especialista Exemplo");
        new EspecialistaView(primaryStage, especialistaFalso).show();
    }*/

    @Override
    public void start(Stage primaryStage) {
        Gestordeprojeto gestorFalso = new Gestordeprojeto();
        gestorFalso.setNome("Nome do Gestor");
        new GestorView(primaryStage, gestorFalso).show();
    }

    /*@Override
    public void start(Stage primaryStage) {
        Membrodepartamentofinanceiro financeiroFalso = new Membrodepartamentofinanceiro();
        financeiroFalso.setNome("Nome do Financeiro");

        new FinanceiroView(primaryStage, financeiroFalso).show();
    }*/


    /*@Override
    public void start(Stage primaryStage) {
        try {
            new LoginView(primaryStage).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


