package com.example.proj2.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "especialista")
public class Especialista {
    @Id
    @Column(name = "idespecialista", nullable = false, precision = 10)
    private BigDecimal id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idespecialista", nullable = false)
    private Tipoespecialista tipoespecialistas;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "telefone", precision = 10)
    private BigDecimal telefone;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Tipoespecialista getTipoespecialistas() {
        return tipoespecialistas;
    }

    public void setTipoespecialistas(Tipoespecialista tipoespecialistas) {
        this.tipoespecialistas = tipoespecialistas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getTelefone() {
        return telefone;
    }

    public void setTelefone(BigDecimal telefone) {
        this.telefone = telefone;
    }

}