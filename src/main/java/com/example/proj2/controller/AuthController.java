package com.example.proj2.controller;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Especialista;
import com.example.proj2.models.Cliente;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.models.Projeto;
import com.example.proj2.models.Solicitacaoprojeto;
import com.example.proj2.models.Orcamentoprojeto;
import com.example.proj2.repository.GestordeprojetoRepository;
import com.example.proj2.repository.EspecialistaRepository;
import com.example.proj2.repository.ClienteRepository;
import com.example.proj2.repository.MembrodepartamentofinanceiroRepository;
import com.example.proj2.repository.ProjetoRepository;
import com.example.proj2.repository.SolicitacaoprojetoRepository;
import com.example.proj2.repository.OrcamentoprojetoRepository;



import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.Optional;


@Controller
public class AuthController {

    @Autowired
    private GestordeprojetoRepository gestorRepo;

    @Autowired
    private EspecialistaRepository especialistaRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private MembrodepartamentofinanceiroRepository membroFinanceiroRepo;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private SolicitacaoprojetoRepository solicitacaoprojetoRepository;

    @Autowired
    private OrcamentoprojetoRepository orcamentoprojetoRepository;


    // FORMULÁRIO DE LOGIN
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    // PROCESSAMENTO DE LOGIN
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

        Membrodepartamentofinanceiro membro = membroFinanceiroRepo.findByEmailAndPassword(email, password);
        if (membro != null) {
            session.setAttribute("user", membro);
            return "membrodepartamentofinanceiro";
        }


        model.addAttribute("erro", "Credenciais inválidas");
        return "login";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // FORMULÁRIO DE REGISTO
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // PROCESSAMENTO DE REGISTO
    @PostMapping("/register")
    public String register(@RequestParam String nome,
                           @RequestParam String email,
                           @RequestParam BigDecimal telefone,
                           @RequestParam(required = false) String endereco,
                           @RequestParam String password,
                           @RequestParam String tipo) {

        switch (tipo) {
            case "cliente":
                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setEmail(email);
                cliente.setTelefone(telefone);
                cliente.setEndereco(endereco);
                cliente.setPassword(password);
                clienteRepo.save(cliente);
                break;

            case "especialista":
                Especialista esp = new Especialista();
                esp.setNome(nome);
                esp.setEmail(email);
                esp.setTelefone(telefone);
                esp.setPassword(password);
                especialistaRepo.save(esp);
                break;

            case "gestor":
                Gestordeprojeto gestor = new Gestordeprojeto();
                gestor.setNome(nome);
                gestor.setEmail(email);
                gestor.setTelefone(telefone);
                gestor.setPassword(password);
                gestorRepo.save(gestor);
                break;

            case "membrodepartamentofinanceiro":
                Membrodepartamentofinanceiro membro = new Membrodepartamentofinanceiro();
                membro.setNome(nome);
                membro.setEmail(email);
                membro.setTelefone(telefone);
                membro.setPassword(password);
                membroFinanceiroRepo.save(membro);
                break;

        }

        return "redirect:/login";
    }


    @GetMapping("/especialista")
    public String menuEspecialista() {
        return "especialista";
    }

    @GetMapping("/cliente")
    public String menuCliente() {
        return "cliente";
    }

    // MENUS
    @GetMapping("/gestor")
    public String menuGestor() {
        return "gestordeprojeto";
    }

    // Listar solicitações
    @GetMapping("/solicitacoesprojeto")
    public String listarSolicitacoesProjeto(Model model) {
        model.addAttribute("solicitacoes", solicitacaoprojetoRepository.findAll());
        return "solicitacoesprojeto"; // Nome do ficheiro HTML
    }

    // Ver detalhes de uma solicitação
    @GetMapping("/solicitacao/{id}")
    public String verDetalhesSolicitacao(@PathVariable BigDecimal id, Model model) {
        Optional<Solicitacaoprojeto> solicitacao = solicitacaoprojetoRepository.findById(id);
        solicitacao.ifPresent(s -> model.addAttribute("solicitacao", s));
        return "detalhessolicitacao"; // Nome do HTML de detalhes
    }

    @GetMapping("/projeto/{id}")
    public String verDetalhesProjeto(@PathVariable BigDecimal id, Model model) {
        Optional<Projeto> projeto = projetoRepository.findById(id);
        if (projeto.isPresent()) {
            model.addAttribute("projeto", projeto.get());
            return "detalhesprojeto"; // deve coincidir com o nome do ficheiro HTML (sem .html)
        }
        return "redirect:/projetosemcurso"; // caso não encontre
    }

    @GetMapping("/projetosemcurso")
    public String listarProjetosEmCurso(Model model) {
        List<Projeto> projetos = projetoRepository.findAll()
                .stream()
                .filter(p -> "Em andamento".equalsIgnoreCase(p.getEstado()))
                .collect(Collectors.toList());

        model.addAttribute("projetos", projetos);
        return "projetosemcurso";
    }


    @GetMapping("/detalhesprojetoemcurso")
    public String detalhesProjetoEmCurso() {
        return "detalhesprojetoemcurso";
    }

    @GetMapping("/projetosparaorcamento")
    public String listarProjetosParaOrcamento(Model model) {
        List<Orcamentoprojeto> orcamentos = orcamentoprojetoRepository.findAll();
        model.addAttribute("orcamentos", orcamentos);
        return "projetosparaorcamento"; // nome do ficheiro .html
    }


    @GetMapping("/detalhesprojetoorcamento/{id}")
    public String detalhesProjetoOrcamento(@PathVariable BigDecimal id, Model model) {
        Optional<Orcamentoprojeto> orcamento = orcamentoprojetoRepository.findById(id);
        if (orcamento.isPresent()) {
            model.addAttribute("orcamento", orcamento.get());
            return "detalhesprojetoorcamento";
        }
        return "redirect:/projetosparaorcamento";
    }

    @GetMapping("/projetosemespera")
    public String listarProjetosEmEspera(Model model) {
        List<Projeto> projetosEmEspera = projetoRepository.findAll()
                .stream()
                .filter(p -> "Em espera".equalsIgnoreCase(p.getEstado()))
                .collect(Collectors.toList());

        model.addAttribute("projetos", projetosEmEspera);
        return "projetosemespera"; // ficheiro .html que mostra os projetos em espera
    }

    @GetMapping("/detalhesprojetoemespera")
    public String detalhesProjetoEmEspera() {
        return "detalhesprojetoemespera";
    }

    @GetMapping("/projetosparaorcamentoespecialista")
    public String projetosOrcamentoEspecialista() {
        return "projetosparaorcamentoespecialista";
    }

    @GetMapping("/detalhesprojetoorcamentoespecialista")
    public String detalhesProjetoConcluido() {
        return "detalhesprojetoorcamentoespecialista"; // nome do HTML
    }

    @GetMapping("/projetosemcursoespecialista")
    public String mostrarProjetosCursoEspecialista() {
        return "projetosemcursoespecialista"; // nome do ficheiro sem extensão
    }

    @GetMapping("/detalhesprojetoemcursoespecialista")
    public String detalhesProjetoCurso() {
        return "detalhesprojetoemcursoespecialista"; // sem .html
    }

    @PostMapping("/eliminarprojeto/{id}")
    public String eliminarProjeto(@PathVariable BigDecimal id) {
        projetoRepository.deleteById(id);
        return "redirect:/projetosemcurso"; // ou a página que estás a usar
    }

}
