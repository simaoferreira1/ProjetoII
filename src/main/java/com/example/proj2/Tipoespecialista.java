package com.example.proj2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "tipoespecialistas")
public class Tipoespecialista {
    @Id
    @Column(name = "idtipoespecialista", nullable = false, precision = 10)
    private BigDecimal id;

    @Column(name = "descricao", length = 50)
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