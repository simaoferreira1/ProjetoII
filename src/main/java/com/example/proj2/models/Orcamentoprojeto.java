package com.example.proj2.models;

import jakarta.persistence.*;
    import org.hibernate.annotations.OnDelete;
    import org.hibernate.annotations.OnDeleteAction;

    import java.math.BigDecimal;
    import java.time.LocalDate;

@Entity
@Table(name = "orcamentoprojeto")
public class Orcamentoprojeto {

    @Id
    @Column(name = "idorcamentoprojeto", nullable = false, precision = 10)
    private BigDecimal id;

    @Column(name = "valortotal", precision = 10)
    private BigDecimal valortotal;

    @Column(name = "dataaprovacao")
    private LocalDate dataaprovacao;

    @Column(name = "estado", length = 20)
    private String estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idprojeto") // esta é a foreign key para a tabela projeto
    private Projeto projeto;

    // Getters e Setters
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
}
