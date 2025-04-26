package com.example.proj2;

import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.services.MembrodepartamentofinanceiroService;
import com.example.proj2.views.ProjetosEmCursoFinanceiroView;
import javafx.application.Application;
import javafx.application.Platform;
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
        // Obter o serviço para buscar um Membrodepartamentofinanceiro
        MembrodepartamentofinanceiroService financeiroService = context.getBean(MembrodepartamentofinanceiroService.class);

        // Buscar um Membrodepartamentofinanceiro do banco de dados (exemplo: primeiro registro)
        Membrodepartamentofinanceiro financeiro = financeiroService.listarMembros().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum membro do departamento financeiro encontrado no banco de dados"));

        // Abrir a ProjetosEmCursoFinanceiroView com o Stage e o Membrodepartamentofinanceiro
        new ProjetosEmCursoFinanceiroView(primaryStage, financeiro).show();
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