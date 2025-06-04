package com.example.proj2.controller.web;

import com.example.proj2.models.Cliente;
import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.repository.SolicitacaoprojetoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class SolicitacaoProjetoController {

    @Autowired
    private SolicitacaoprojetoRepository repo;

    @GetMapping("")
    public String dashboard(HttpSession session, Model model) {
        Cliente c = (Cliente) session.getAttribute("cliente");
        if (c == null) return "redirect:/login";
        model.addAttribute("cliente", c);
        return "web/ClienteDashboardView";
    }

    @GetMapping("/solicitar")
    public String mostrarFormulario(HttpSession session, Model model) {
        Cliente c = (Cliente) session.getAttribute("cliente");
        if (c == null) return "redirect:/login";
        model.addAttribute("cliente", c);
        return "web/solicitarProjeto";
    }

    @PostMapping("/solicitar")
    public String submeterProjeto(String nome,
                                  String descricao,
                                  HttpSession session,
                                  RedirectAttributes attrs) {
        Cliente c = (Cliente) session.getAttribute("cliente");
        if (c == null) return "redirect:/login";

        try {
            Solicitacaoprojeto sp = new Solicitacaoprojeto();
            sp.setNome(nome);
            sp.setDescricao(descricao);
            sp.setDatasolicitacao(LocalDate.now());
            sp.setEstado("Pendente");
            sp.setCliente(c);
            repo.save(sp);
            attrs.addFlashAttribute("mensagemSucesso", "Projeto enviado com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("mensagemErro", "Erro ao enviar projeto.");
        }
        return "redirect:/cliente/solicitar";
    }

    @GetMapping("/projetos")
    public String listarProjetos(HttpSession session, Model model) {
        Cliente c = (Cliente) session.getAttribute("cliente");
        if (c == null) return "redirect:/login";
        List<Solicitacaoprojeto> list = repo.findByCliente(c);
        model.addAttribute("cliente", c);
        model.addAttribute("projetos", list);
        return "web/listarProjetos";
    }
}
