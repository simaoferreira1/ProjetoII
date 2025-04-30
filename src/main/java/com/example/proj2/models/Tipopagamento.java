package com.example.proj2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "tipopagamento")
public class Tipopagamento {
    @Id
    @Column(name = "idtipopagamento", nullable = false, precision = 10)
    private Integer id;

    @Column(name = "descricao", length = 50)
    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}