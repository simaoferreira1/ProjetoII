package com.example.proj2.controller.web;

import com.example.proj2.models.Cliente;
import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.repository.SolicitacaoprojetoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.time.LocalDate;

@Controller
@RequestMapping("/cliente")
public class SolicitacaoProjetoController {

    @Autowired
    private SolicitacaoprojetoRepository solicitacaoProjetoRepository;

    @GetMapping("/solicitar")
    public String mostrarFormulario(HttpSession session, Model model) {
        if (session.getAttribute("cliente") == null) {
            return "redirect:/login";
        }
        return "web/solicitarProjeto";
    }

    @PostMapping("/solicitar")
    public String submeterProjeto(@RequestParam String nome,
                                  @RequestParam String descricao,
                                  @RequestParam(required = false) String localreuniao,
                                  HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/login";
        }

        Solicitacaoprojeto sp = new Solicitacaoprojeto();
        sp.setNome(nome);
        sp.setDescricao(descricao);
        sp.setLocalreuniao(localreuniao);
        sp.setCliente(cliente);
        sp.setDatasolicitacao(LocalDate.now());
        sp.setEstado("Pendente"); // ou outro estado padrão

        solicitacaoProjetoRepository.save(sp);

        return "redirect:/cliente/dashboard";
    }

    @GetMapping("/projetos")
    public String listarProjetos(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) return "redirect:/login";

        List<Solicitacaoprojeto> projetos = solicitacaoProjetoRepository.findByCliente(cliente);
        model.addAttribute("projetos", projetos);
        model.addAttribute("cliente", cliente);
        return "web/listarProjetos";
    }

}
