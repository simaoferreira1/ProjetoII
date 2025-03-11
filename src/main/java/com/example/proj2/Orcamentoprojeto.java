package com.example.proj2;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orcamentoprojeto")
public class Orcamentoprojeto {
    @Id
    @Column(name = "idorcamentoprojeto", nullable = false, precision = 5)
    private BigDecimal id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idorcamentoprojeto", nullable = false)
    private com.example.proj2.Projeto projeto;

    @Column(name = "valortotal", precision = 10)
    private BigDecimal valortotal;

    @Column(name = "dataaprovacao")
    private LocalDate dataaprovacao;

    @Column(name = "estado", length = 20)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idprojeto")
    private com.example.proj2.Projeto idprojeto;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public com.example.proj2.Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(com.example.proj2.Projeto projeto) {
        this.projeto = projeto;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public LocalDate getDataaprovacao() {
        return dataaprovacao;
    }

    public void setDataaprovacao(LocalDate dataaprovacao) {
        this.dataaprovacao = dataaprovacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public com.example.proj2.Projeto getIdprojeto() {
        return idprojeto;
    }

    public void setIdprojeto(com.example.proj2.Projeto idprojeto) {
        this.idprojeto = idprojeto;
    }

}