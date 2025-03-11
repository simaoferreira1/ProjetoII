package com.example.proj2.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "projeto")
public class Projeto {
    @Id
    @Column(name = "idprojeto", nullable = false, precision = 5)
    private BigDecimal id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idprojeto", nullable = false)
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

    @Column(name = "idgestor", precision = 10)
    private BigDecimal idgestor;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
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

    public BigDecimal getIdgestor() {
        return idgestor;
    }

    public void setIdgestor(BigDecimal idgestor) {
        this.idgestor = idgestor;
    }

}