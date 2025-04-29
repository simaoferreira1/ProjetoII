package com.example.proj2.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "idcliente", nullable = false, precision = 10)
    private BigDecimal id;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "telefone", precision = 10)
    private BigDecimal telefone;

    @Column(name = "endereco", length = 100)
    private String endereco;

    @Column(name = "password", length = 20)
    private String password;

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
