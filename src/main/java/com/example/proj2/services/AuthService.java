package com.example.proj2.services;

import com.example.proj2.models.*;
import com.example.proj2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private GestordeprojetoRepository gestorRepo;

    @Autowired
    private EspecialistaRepository especialistaRepo;

    @Autowired
    private MembrodepartamentofinanceiroRepository financeiroRepo;

    public Object autenticar(String email, String password) {
        long start = System.currentTimeMillis(); // ← tempo inicial
        System.out.println("▶️ Iniciando autenticação...");

        Gestordeprojeto gestor = gestorRepo.findByEmailAndPassword(email, password);
        if (gestor != null) {
            System.out.println("✅ Encontrado: Gestor");
            logTempo(start);
            return gestor;
        }

        Especialista esp = especialistaRepo.findByEmailAndPassword(email, password);
        if (esp != null) {
            System.out.println("✅ Encontrado: Especialista");
            logTempo(start);
            return esp;
        }

        Membrodepartamentofinanceiro fin = financeiroRepo.findByEmailAndPassword(email, password);
        if (fin != null) {
            System.out.println("✅ Encontrado: Financeiro");
            logTempo(start);
            return fin;
        }

        System.out.println("❌ Nenhum utilizador encontrado.");
        logTempo(start);
        return null;
    }

    private void logTempo(long start) {
        long fim = System.currentTimeMillis();
        System.out.println("⏱ Tempo de autenticação: " + (fim - start) + " ms");
    }

}
