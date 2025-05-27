package com.example.proj2.controller.web;

import com.example.proj2.models.Cliente;
import com.example.proj2.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginWebController {

    @Autowired
    private ClienteRepository clienteRepo;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "erro", required = false) String erro, Model model) {
        if (erro != null) {
            model.addAttribute("erro", "Credenciais inválidas.");
        }
        return "web/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        Cliente cliente = clienteRepo.findByEmailAndPassword(email, password);
        if (cliente != null) {
            // Aqui podes guardar o cliente em sessão, se quiseres
            // session.setAttribute("cliente", cliente); // se usares HttpSession
            return "redirect:/dashboard"; // Ou a página principal após login
        } else {
            model.addAttribute("erro", "Credenciais inválidas.");
            return "web/login";
        }
    }
}
