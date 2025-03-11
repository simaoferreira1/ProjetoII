package com.example.proj2.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "utilizador")
public class Utilizador {
    @Id
    @Column(name = "idutilizador", nullable = false, precision = 10)
    private BigDecimal id;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "\"user\"", length = 50)
    private String user;

    @Column(name = "password", length = 20)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipoutilizador")
    private Tipoutilizador idtipoutilizador;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Tipoutilizador getIdtipoutilizador() {
        return idtipoutilizador;
    }

    public void setIdtipoutilizador(Tipoutilizador idtipoutilizador) {
        this.idtipoutilizador = idtipoutilizador;
    }

}