package com.example.proj2.controller.web;

import com.example.proj2.models.Cliente;
import com.example.proj2.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterWebController {

    @Autowired
    private ClienteRepository clienteRepo;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "web/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("cliente") Cliente cliente) {
        clienteRepo.save(cliente);
        return "redirect:/login?registo=sucesso";
    }
}
