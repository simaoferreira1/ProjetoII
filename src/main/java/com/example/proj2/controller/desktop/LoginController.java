package com.example.proj2.controller.desktop;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.services.AuthService;
import com.example.proj2.repository.GestordeprojetoRepository;
import com.example.proj2.repository.EspecialistaRepository;
import com.example.proj2.repository.MembrodepartamentofinanceiroRepository;
import com.example.proj2.views.GestorView;
import com.example.proj2.views.EspecialistaView;
import com.example.proj2.views.FinanceiroView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javafx.application.Platform;

import java.math.BigDecimal;

@Controller
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private GestordeprojetoRepository gestorRepo;

    @Autowired
    private EspecialistaRepository especialistaRepo;

    @Autowired
    private MembrodepartamentofinanceiroRepository membroFinanceiroRepo;

    public String login(String email, String password, Stage stage) {
        Object user = authService.autenticar(email, password);
        long inicio = System.currentTimeMillis();
        System.out.println("Início do login");

        if (user == null) {
            return "Credenciais inválidas!";
        }

        // Redireciona para a view adequada conforme o tipo de utilizador
        if (user instanceof Gestordeprojeto) {
            Gestordeprojeto gestor = (Gestordeprojeto) user;
            Platform.runLater(() -> new GestorView(stage, gestor).show());
        }
        else if (user instanceof Especialista) {
            Especialista especialista = (Especialista) user;
            Platform.runLater(() -> new EspecialistaView(stage, especialista).show());
        }
        else if (user instanceof Membrodepartamentofinanceiro) {
            Membrodepartamentofinanceiro financeiro = (Membrodepartamentofinanceiro) user;
            Platform.runLater(() -> new FinanceiroView(stage, financeiro).show());
        }
        else {
            return "Tipo de utilizador não suportado!";
        }

        long fim = System.currentTimeMillis();
        System.out.println("Login processado em " + (fim - inicio) + " ms");

        return null;
    }

    public String registrar(String nome, String email, String telefone,
                            String password, String tipo) {
        try {
            // Validação básica dos campos
            if (nome == null || nome.trim().isEmpty()) return "Nome é obrigatório!";
            if (email == null || email.trim().isEmpty()) return "E-mail é obrigatório!";
            if (telefone == null || telefone.trim().isEmpty()) return "Telefone é obrigatório!";
            if (password == null || password.trim().isEmpty()) return "Palavra-passe é obrigatória!";
            if (tipo == null || tipo.trim().isEmpty()) return "Tipo de utilizador é obrigatório!";

            if (!email.contains("@") || !email.contains(".")) return "E-mail inválido!";
            if (!telefone.matches("\\d+")) return "Telefone deve conter apenas números!";

            BigDecimal telefoneBD = new BigDecimal(telefone);

            // Cria o tipo de utilizador correto
            switch (tipo) {
                case "Gestor de Projeto":
                    Gestordeprojeto gestor = new Gestordeprojeto();
                    gestor.setNome(nome.trim());
                    gestor.setEmail(email.trim().toLowerCase());
                    gestor.setTelefone(telefoneBD);
                    gestor.setPassword(password);
                    gestorRepo.save(gestor);
                    break;

                case "Especialista":
                    Especialista esp = new Especialista();
                    esp.setNome(nome.trim());
                    esp.setEmail(email.trim().toLowerCase());
                    esp.setTelefone(telefoneBD);
                    esp.setPassword(password);
                    especialistaRepo.save(esp);
                    break;

                case "Membro Departamento Financeiro":
                    Membrodepartamentofinanceiro membro = new Membrodepartamentofinanceiro();
                    membro.setNome(nome.trim());
                    membro.setEmail(email.trim().toLowerCase());
                    membro.setTelefone(telefoneBD);
                    membro.setPassword(password);
                    membroFinanceiroRepo.save(membro);
                    break;

                default:
                    return "Tipo de utilizador não suportado!";
            }

            return "Utilizador registado com sucesso!";

        } catch (NumberFormatException e) {
            return "Telefone inválido!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao registar utilizador: " + e.getMessage();
        }
    }
}
