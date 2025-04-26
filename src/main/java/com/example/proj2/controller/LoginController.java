package com.example.proj2.controller;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.services.AuthService;
import com.example.proj2.views.GestorView;
import com.example.proj2.views.EspecialistaView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.application.Platform;
import com.example.proj2.views.FinanceiroView;




@Component
public class LoginController {

    @Autowired
    private AuthService authService;

    public String login(String email, String password, Stage stage) {
        Object user = authService.autenticar(email, password);
        long inicio = System.currentTimeMillis();
        System.out.println("🔐 Início do login");


        if (user == null) {
            return "Credenciais inválidas!";
        }

        if (user instanceof Gestordeprojeto) {
            Gestordeprojeto gestor = (Gestordeprojeto) user;
            Platform.runLater(() -> new GestorView(stage, gestor).show());
        } else if (user instanceof Especialista) {
            Especialista especialista = (Especialista) user;
            Platform.runLater(() -> new EspecialistaView(stage, especialista).show());
        } else if (user instanceof Membrodepartamentofinanceiro) {
            Membrodepartamentofinanceiro financeiro = (Membrodepartamentofinanceiro) user;
            Platform.runLater(() -> new FinanceiroView(stage, financeiro).show());
        } else {
            return "Tipo de utilizador não suportado!";
        }

        long fim = System.currentTimeMillis();
        System.out.println("✅ Login processado em " + (fim - inicio) + " ms");

        return null;
    }
}
