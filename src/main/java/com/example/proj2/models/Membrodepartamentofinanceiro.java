package com.example.proj2.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "membrodepartamentofinanceiro")
public class Membrodepartamentofinanceiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmembro", nullable = false)
    private Integer id;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "telefone", precision = 10)
    private BigDecimal telefone;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public BigDecimal getTelefone() { return telefone; }
    public void setTelefone(BigDecimal telefone) { this.telefone = telefone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
