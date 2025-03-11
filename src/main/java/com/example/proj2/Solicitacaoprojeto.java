package com.example.proj2;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "solicitacaoprojeto")
public class Solicitacaoprojeto {
    @Id
    @Column(name = "idsolicitacao", nullable = false, precision = 5)
    private BigDecimal id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idsolicitacao", nullable = false)
    private Cliente cliente;

    @Column(name = "datasolicitacao")
    private LocalDate datasolicitacao;

    @Column(name = "localreuniao", length = 20)
    private String localreuniao;

    @Column(name = "estado", length = 20)
    private String estado;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDatasolicitacao() {
        return datasolicitacao;
    }

    public void setDatasolicitacao(LocalDate datasolicitacao) {
        this.datasolicitacao = datasolicitacao;
    }

    public String getLocalreuniao() {
        return localreuniao;
    }

    public void setLocalreuniao(String localreuniao) {
        this.localreuniao = localreuniao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}