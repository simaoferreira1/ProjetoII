package com.example.proj2.controller;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Cliente;
import com.example.proj2.repository.GestordeprojetoRepository;
import com.example.proj2.repository.EspecialistaRepository;
import com.example.proj2.repository.ClienteRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private GestordeprojetoRepository gestorRepo;

    @Autowired
    private EspecialistaRepository especialistaRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

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

        model.addAttribute("erro", "Credenciais inválidas");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/gestor")
    public String menuGestor() {
        return "gestordeprojeto";
    }

    @GetMapping("/especialista")
    public String menuEspecialista() {
        return "especialista";
    }

    @GetMapping("/cliente")
    public String menuCliente() {
        return "cliente";
    }
}
