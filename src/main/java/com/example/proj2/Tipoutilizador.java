package com.example.proj2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "tipoutilizador")
public class Tipoutilizador {
    @Id
    @Column(name = "idtipoutilizador", nullable = false, precision = 10)
    private BigDecimal id;

    @Column(name = "descricao", length = Integer.MAX_VALUE)
    private String descricao;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}