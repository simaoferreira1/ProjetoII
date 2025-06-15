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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cliente")
public class SolicitacaoProjetoController {

    @Autowired
    private SolicitacaoprojetoRepository solicitacaoRepo;

    @Autowired
    private ProjetoRepository projetoRepo;

    // Dashboard do cliente
    @GetMapping("")
    public String dashboard(HttpSession session, Model model) {
        Cliente c = (Cliente) session.getAttribute("cliente");
        if (c == null) return "redirect:/login";
        model.addAttribute("cliente", c);
        return "web/ClienteDashboardView";
    }

    // Formulário para solicitar novo projeto
    @GetMapping("/solicitar")
    public String mostrarFormulario(HttpSession session, Model model) {
        Cliente c = (Cliente) session.getAttribute("cliente");
        if (c == null) return "redirect:/login";
        model.addAttribute("cliente", c);
        return "web/solicitarProjeto";
    }

    // Submissão de nova solicitação de projeto
    @PostMapping("/solicitar")
    public String submeterProjeto(String nome,
                                  String descricao,
                                  String localizacao,
                                  HttpSession session,
                                  RedirectAttributes attrs) {
        Cliente c = (Cliente) session.getAttribute("cliente");
        if (c == null) return "redirect:/login";

        try {
            Solicitacaoprojeto sp = new Solicitacaoprojeto();
            sp.setNome(nome);
            sp.setDescricao(descricao);
            sp.setLocalizacao(localizacao);
            sp.setDatasolicitacao(LocalDate.now());
            sp.setEstado("Pendente");
            sp.setCliente(c);
            solicitacaoRepo.save(sp);
            attrs.addFlashAttribute("mensagemSucesso", "Projeto enviado com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("mensagemErro", "Erro ao enviar projeto.");
        }
        return "redirect:/cliente/solicitar";
    }

    // Lista dos projetos do cliente, organizados por estado
    @GetMapping("/projetos")
    public String listarProjetos(HttpSession session, Model model) {
        Cliente c = (Cliente) session.getAttribute("cliente");
        if (c == null) return "redirect:/login";

        List<Solicitacaoprojeto> pendentes = solicitacaoRepo.findByCliente(c);
        List<Projeto> todosProjetos = projetoRepo.findByIdcliente(c);

        List<Projeto> aprovados = todosProjetos.stream()
                .filter(p -> {
                    String estado = p.getEstado().trim().toLowerCase();
                    return estado.equals("em curso") || estado.equals("em pré-planeamento");
                })
                .collect(Collectors.toList());

        List<Projeto> terminados = todosProjetos.stream()
                .filter(p -> p.getEstado().trim().equalsIgnoreCase("terminado"))
                .collect(Collectors.toList());

        model.addAttribute("cliente", c);
        model.addAttribute("pendentes", pendentes);
        model.addAttribute("aprovados", aprovados);
        model.addAttribute("terminados", terminados);

        return "web/listarProjetos";
    }

    // Consulta de uma solicitação específica
    @GetMapping("/solicitacoes/{id}")
    public String consultarSolicitacao(@PathVariable int id, HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");

        Optional<Solicitacaoprojeto> solicitacao = solicitacaoRepo.findById(id);
        if (solicitacao.isPresent() &&
                solicitacao.get().getCliente().getId().equals(cliente.getId())) {

            model.addAttribute("solicitacao", solicitacao.get());
            return "web/detalheSolicitacao";
        }

        return "redirect:/cliente/projetos";
    }
}
