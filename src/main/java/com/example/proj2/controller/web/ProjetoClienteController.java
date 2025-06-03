package com.example.proj2.controller.web;

import com.example.proj2.models.Cliente;
import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.repository.SolicitacaoprojetoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cliente/projetos")
public class ProjetoClienteController {

    @Autowired
    private SolicitacaoprojetoRepository solicitacaoprojetoRepository;

    // Abre a página com os detalhes do projeto
    @GetMapping("/{id}")
    public String abrirProjeto(@PathVariable("id") Integer id, Model model, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) return "redirect:/login";

        Solicitacaoprojeto projeto = solicitacaoprojetoRepository.findById(id).orElse(null);
        if (projeto == null || !projeto.getCliente().getId().equals(cliente.getId())) {
            return "redirect:/cliente/projetos";
        }

        model.addAttribute("projeto", projeto);
        return "web/detalheProjeto";
    }

    // Elimina o projeto e redireciona com mensagem
    @PostMapping("/eliminar/{id}")
    public String eliminarProjeto(@PathVariable("id") Integer id, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) return "redirect:/login";

        Solicitacaoprojeto projeto = solicitacaoprojetoRepository.findById(id).orElse(null);
        if (projeto != null && projeto.getCliente().getId().equals(cliente.getId())) {
            solicitacaoprojetoRepository.deleteById(id);
        }

        return "redirect:/cliente/projetos?eliminado=sucesso";
    }
}
