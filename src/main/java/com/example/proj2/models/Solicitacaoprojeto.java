package com.example.proj2.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "solicitacaoprojeto")
public class Solicitacaoprojeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsolicitacao", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idcliente", nullable = false)
    private Cliente cliente;

    @Column(name = "datasolicitacao")
    private LocalDate datasolicitacao;

    @Column(name = "localreuniao", length = 20)
    private String localreuniao;

    @Column(name = "localizacao", length = 100) // <-- novo campo
    private String localizacao;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "descricao", length = 500)
    private String descricao;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDate getDatasolicitacao() { return datasolicitacao; }
    public void setDatasolicitacao(LocalDate datasolicitacao) { this.datasolicitacao = datasolicitacao; }

    public String getLocalreuniao() { return localreuniao; }
    public void setLocalreuniao(String localreuniao) { this.localreuniao = localreuniao; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
