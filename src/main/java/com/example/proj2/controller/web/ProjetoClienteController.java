package com.example.proj2.controller.web;

import com.example.proj2.models.Cliente;
import com.example.proj2.models.Projeto;
import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.repository.ProjetoRepository;
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

    @Autowired
    private ProjetoRepository projetoRepository;

    // Abre o detalhe de um projeto (pendente ou oficial)
    @GetMapping("/{id}")
    public String abrirProjeto(@PathVariable("id") Integer id, Model model, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) return "redirect:/login";

        model.addAttribute("cliente", cliente);

        // Verifica se é um projeto pendente
        Solicitacaoprojeto pendente = solicitacaoprojetoRepository.findById(id).orElse(null);
        if (pendente != null && pendente.getCliente().getId().equals(cliente.getId())) {
            model.addAttribute("projeto", pendente);
            model.addAttribute("tipo", "pendente");
            return "web/detalheProjeto";
        }

        // Verifica se é um projeto
        Projeto projeto = projetoRepository.findById(id).orElse(null);
        if (projeto != null && projeto.getIdcliente().getId().equals(cliente.getId())) {
            model.addAttribute("projeto", projeto);
            model.addAttribute("tipo", "oficial");
            return "web/detalheProjeto";
        }

        return "redirect:/cliente/projetos";
    }

    // Elimina projeto pendente ou terminado
    @PostMapping("/eliminar/{id}")
    public String eliminarProjeto(@PathVariable("id") Integer id, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) return "redirect:/login";

        // Elimina se for pendente
        Solicitacaoprojeto pendente = solicitacaoprojetoRepository.findById(id).orElse(null);
        if (pendente != null && pendente.getCliente().getId().equals(cliente.getId())) {
            solicitacaoprojetoRepository.deleteById(id);
            return "redirect:/cliente/projetos?eliminado=sucesso";
        }

        // Elimina se for terminado
        Projeto projeto = projetoRepository.findById(id).orElse(null);
        if (projeto != null &&
                projeto.getIdcliente().getId().equals(cliente.getId()) &&
                projeto.getEstado().trim().equalsIgnoreCase("terminado")) {
            projetoRepository.delete(projeto);
            return "redirect:/cliente/projetos?eliminado=sucesso";
        }

        return "redirect:/cliente/projetos";
    }
}
