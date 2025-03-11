package com.example.proj2;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orcamentos")
public class Orcamento {
    @Id
    @Column(name = "idorcamentototal", nullable = false, precision = 10)
    private BigDecimal id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idorcamentototal", nullable = false)
    private com.example.proj2.Projetostecnico projetostecnicos;

    @Column(name = "descricao", length = 50)
    private String descricao;

    @Column(name = "valorestimado", precision = 10)
    private BigDecimal valorestimado;

    @Column(name = "datacriacao")
    private LocalDate datacriacao;

    @Column(name = "estado", length = 50)
    private String estado;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public com.example.proj2.Projetostecnico getProjetostecnicos() {
        return projetostecnicos;
    }

    public void setProjetostecnicos(com.example.proj2.Projetostecnico projetostecnicos) {
        this.projetostecnicos = projetostecnicos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorestimado() {
        return valorestimado;
    }

    public void setValorestimado(BigDecimal valorestimado) {
        this.valorestimado = valorestimado;
    }

    public LocalDate getDatacriacao() {
        return datacriacao;
    }

    public void setDatacriacao(LocalDate datacriacao) {
        this.datacriacao = datacriacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}