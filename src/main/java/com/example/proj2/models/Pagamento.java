package com.example.proj2.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @Column(name = "idpagamento", nullable = false, precision = 10)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idpagamento", nullable = false)
    private Cliente cliente;

    @Column(name = "valor", precision = 10)
    private BigDecimal valor;

    @Column(name = "datapagamento")
    private LocalDate datapagamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDatapagamento() {
        return datapagamento;
    }

    public void setDatapagamento(LocalDate datapagamento) {
        this.datapagamento = datapagamento;
    }

}