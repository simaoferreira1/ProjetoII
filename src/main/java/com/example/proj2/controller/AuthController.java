package com.example.proj2.controller;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Cliente;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.repository.GestordeprojetoRepository;
import com.example.proj2.repository.EspecialistaRepository;
import com.example.proj2.repository.ClienteRepository;
import com.example.proj2.repository.MembrodepartamentofinanceiroRepository;



import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class AuthController {

    @Autowired
    private GestordeprojetoRepository gestorRepo;

    @Autowired
    private EspecialistaRepository especialistaRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private MembrodepartamentofinanceiroRepository membroFinanceiroRepo;


    // FORMULÁRIO DE LOGIN
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    // PROCESSAMENTO DE LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Gestordeprojeto gestor = gestorRepo.findByEmailAndPassword(email, password);
        if (gestor != null) {
            session.setAttribute("user", gestor);
            return "gestordeprojeto";
        }

        Especialista especialista = especialistaRepo.findByEmailAndPassword(email, password);
        if (especialista != null) {
            session.setAttribute("user", especialista);
            return "especialista";
        }

        Cliente cliente = clienteRepo.findByEmailAndPassword(email, password);
        if (cliente != null) {
            session.setAttribute("user", cliente);
            return "cliente";
        }

        Membrodepartamentofinanceiro membro = membroFinanceiroRepo.findByEmailAndPassword(email, password);
        if (membro != null) {
            session.setAttribute("user", membro);
            return "membrodepartamentofinanceiro";
        }


        model.addAttribute("erro", "Credenciais inválidas");
        return "login";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // FORMULÁRIO DE REGISTO
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // PROCESSAMENTO DE REGISTO
    @PostMapping("/register")
    public String register(@RequestParam String nome,
                           @RequestParam String email,
                           @RequestParam BigDecimal telefone,
                           @RequestParam(required = false) String endereco,
                           @RequestParam String password,
                           @RequestParam String tipo) {

        switch (tipo) {
            case "cliente":
                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setEmail(email);
                cliente.setTelefone(telefone);
                cliente.setEndereco(endereco);
                cliente.setPassword(password);
                clienteRepo.save(cliente);
                break;

            case "especialista":
                Especialista esp = new Especialista();
                esp.setNome(nome);
                esp.setEmail(email);
                esp.setTelefone(telefone);
                esp.setPassword(password);
                especialistaRepo.save(esp);
                break;

            case "gestor":
                Gestordeprojeto gestor = new Gestordeprojeto();
                gestor.setNome(nome);
                gestor.setEmail(email);
                gestor.setTelefone(telefone);
                gestor.setPassword(password);
                gestorRepo.save(gestor);
                break;

            case "membrodepartamentofinanceiro":
                Membrodepartamentofinanceiro membro = new Membrodepartamentofinanceiro();
                membro.setNome(nome);
                membro.setEmail(email);
                membro.setTelefone(telefone);
                membro.setPassword(password);
                membroFinanceiroRepo.save(membro);
                break;

        }

        return "redirect:/login";
    }

    @GetMapping("/especialista")
    public String menuEspecialista() {
        return "especialista";
    }

    @GetMapping("/cliente")
    public String menuCliente() {
        return "cliente";
    }

    // MENUS
    @GetMapping("/gestor")
    public String menuGestor() {
        return "gestordeprojeto";
    }

    @GetMapping("/gestor/solicitacoesprojeto")
    public String mostrarProjetosSolicitados(Model model) {
        // aqui podes carregar uma lista de projetos, se quiseres
        return "solicitacoesprojeto"; // corresponde ao ficheiro solicitacoesprojeto.html
    }

    @GetMapping("/detalhesprojeto")
    public String detalhesProjeto() {
        return "detalhesprojeto";
    }

    @GetMapping("/projetosemcurso")
    public String projetosEmCurso() {
        return "projetosemcurso";
    }

    @GetMapping("/detalhesprojetoemcurso")
    public String detalhesProjetoEmCurso() {
        return "detalhesprojetoemcurso";
    }

    @GetMapping("/projetosparaorcamento")
    public String listarProjetosParaOrcamento() {
        return "projetosparaorcamento"; // nome do ficheiro .html sem a extensão
    }

    @GetMapping("/detalhesprojetoorcamento")
    public String detalhesProjetoOrcamento() {
        return "detalhesprojetoorcamento";
    }

    @GetMapping("/projetosemespera")
    public String listarProjetosEmEspera() {
        return "projetosemespera";
    }

    @GetMapping("/detalhesprojetoemespera")
    public String detalhesProjetoEmEspera() {
        return "detalhesprojetoemespera";
    }

    @GetMapping("/projetosparaorcamentoespecialista")
    public String projetosOrcamentoEspecialista() {
        return "projetosparaorcamentoespecialista";
    }

    @GetMapping("/detalhesprojetoorcamentoespecialista")
    public String detalhesProjetoConcluido() {
        return "detalhesprojetoorcamentoespecialista"; // nome do HTML
    }

    @GetMapping("/projetosemcursoespecialista")
    public String mostrarProjetosCursoEspecialista() {
        return "projetosemcursoespecialista"; // nome do ficheiro sem extensão
    }

    @GetMapping("/detalhesprojetoemcursoespecialista")
    public String detalhesProjetoCurso() {
        return "detalhesprojetoemcursoespecialista"; // sem .html
    }


}
