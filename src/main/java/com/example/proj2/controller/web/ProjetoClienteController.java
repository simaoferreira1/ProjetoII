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

    @GetMapping("/{id}")
    public String abrirProjeto(@PathVariable("id") Integer id, Model model, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) return "redirect:/login";

        // Adicionar cliente ao model para o sidebar funcionar corretamente
        model.addAttribute("cliente", cliente);

        // Tenta primeiro encontrar como projeto pendente
        Solicitacaoprojeto pendente = solicitacaoprojetoRepository.findById(id).orElse(null);
        if (pendente != null && pendente.getCliente().getId().equals(cliente.getId())) {
            model.addAttribute("projeto", pendente);
            model.addAttribute("tipo", "pendente");
            return "web/detalheProjeto";
        }

        // Tenta como projeto oficial (em curso, pré-planeamento ou terminado)
        Projeto projeto = projetoRepository.findById(id).orElse(null);
        if (projeto != null && projeto.getIdcliente().getId().equals(cliente.getId())) {
            model.addAttribute("projeto", projeto);
            model.addAttribute("tipo", "oficial");
            return "web/detalheProjeto";
        }

        return "redirect:/cliente/projetos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarProjeto(@PathVariable("id") Integer id, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) return "redirect:/login";

        // Tenta eliminar se for projeto pendente
        Solicitacaoprojeto pendente = solicitacaoprojetoRepository.findById(id).orElse(null);
        if (pendente != null && pendente.getCliente().getId().equals(cliente.getId())) {
            solicitacaoprojetoRepository.deleteById(id);
            return "redirect:/cliente/projetos?eliminado=sucesso";
        }

        // Tenta eliminar se for projeto terminado
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
