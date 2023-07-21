package eventify.api_spring.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_pagina")
    private Pagina pagina;

    public Acesso(Integer id, LocalDate dataCriacao, Pagina pagina) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.pagina = pagina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Pagina pagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }
}
