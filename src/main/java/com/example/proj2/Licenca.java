package com.example.proj2;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "licenca")
public class Licenca {
    @Id
    @Column(name = "idlicenca", nullable = false, precision = 10)
    private BigDecimal id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idlicenca", nullable = false)
    private com.example.proj2.Projeto projeto;

    @Column(name = "dataemissao")
    private LocalDate dataemissao;

    @Column(name = "datavalidade")
    private LocalDate datavalidade;

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

    public LocalDate getDataemissao() {
        return dataemissao;
    }

    public void setDataemissao(LocalDate dataemissao) {
        this.dataemissao = dataemissao;
    }

    public LocalDate getDatavalidade() {
        return datavalidade;
    }

    public void setDatavalidade(LocalDate datavalidade) {
        this.datavalidade = datavalidade;
    }

}