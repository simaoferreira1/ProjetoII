package com.example.proj2.controller.web;

import com.example.proj2.models.Cliente;
import com.example.proj2.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginWebController {

    @Autowired
    private ClienteRepository clienteRepo;

    // Mostra o formulário de login
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "erro", required = false) String erro, Model model) {
        if (erro != null) {
            model.addAttribute("erro", "Credenciais inválidas.");
        }
        return "web/login";
    }

    // Processa o login do cliente
    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        Cliente cliente = clienteRepo.findByEmailAndPassword(email, password);
        if (cliente != null) {
            session.setAttribute("cliente", cliente);
            return "redirect:/cliente/dashboard"; // Redireciona para o dashboard do cliente
        } else {
            model.addAttribute("erro", "Credenciais inválidas.");
            return "web/login";
        }
    }

    // Mostra o dashboard do cliente (apenas se autenticado)
    @GetMapping("/cliente/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login?erro";
        }
        model.addAttribute("cliente", cliente);
        return "web/clienteDashboardView"; // View do dashboard do cliente
    }
}
