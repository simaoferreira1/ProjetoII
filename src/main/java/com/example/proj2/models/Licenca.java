package com.example.proj2.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "licenca")
public class Licenca {

    @Id
    @Column(name = "idlicenca", nullable = false, precision = 10)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idlicenca", nullable = false)
    private Projeto projeto;

    @Column(name = "dataemissao", nullable = false)
    private LocalDate dataemissao;

    @Column(name = "datavalidade", nullable = false)
    private LocalDate datavalidade;

    @Version
    @Column(name = "versao")
    private Integer versao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
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

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }
}
