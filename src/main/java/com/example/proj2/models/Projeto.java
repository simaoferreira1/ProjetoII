package com.example.proj2.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprojeto", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idgestor", nullable = false)
    private Gestordeprojeto gestordeprojeto;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "descricao", length = 100)
    private String descricao;

    @Column(name = "datainicio")
    private LocalDate datainicio;

    @Column(name = "datafimprevista")
    private LocalDate datafimprevista;

    @Column(name = "localizacao", length = 50)
    private String localizacao;

    @Column(name = "estado", length = 50)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente")
    private Cliente idcliente;

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Gestordeprojeto getGestordeprojeto() {
        return gestordeprojeto;
    }

    public void setGestordeprojeto(Gestordeprojeto gestordeprojeto) {
        this.gestordeprojeto = gestordeprojeto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(LocalDate datainicio) {
        this.datainicio = datainicio;
    }

    public LocalDate getDatafimprevista() {
        return datafimprevista;
    }

    public void setDatafimprevista(LocalDate datafimprevista) {
        this.datafimprevista = datafimprevista;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }
}
