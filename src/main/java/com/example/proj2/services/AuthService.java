package com.example.proj2.services;

import com.example.proj2.models.Especialista;
import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.repository.EspecialistaRepository;
import com.example.proj2.repository.GestordeprojetoRepository;
import com.example.proj2.repository.MembrodepartamentofinanceiroRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private GestordeprojetoRepository gestorRepo = new GestordeprojetoRepository();
    private EspecialistaRepository especialistaRepo = new EspecialistaRepository();
    private MembrodepartamentofinanceiroRepository financeiroRepo = new MembrodepartamentofinanceiroRepository();

    public Object autenticar(String email, String password) {
        Gestordeprojeto gestor = gestorRepo.findByEmailAndPassword(email, password);
        if (gestor != null) return gestor;

        Especialista esp = especialistaRepo.findByEmailAndPassword(email, password);
        if (esp != null) return esp;

        Membrodepartamentofinanceiro fin = financeiroRepo.findByEmailAndPassword(email, password);
        if (fin != null) return fin;

        return null;
    }
}
