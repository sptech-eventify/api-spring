package eventify.api_spring.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @FutureOrPresent
    private LocalDate data;

    @NotNull
    @DecimalMin("0.01")
    private Double preco;
    private String avalicacao;
    private Double nota;
    private String status;
    private String motivoNaoAceito;
    private Boolean isFormularioDinamico;
    @ManyToOne
    @JoinColumn(name = "id_buffet")
    private Buffet buffet;
    @OneToOne
    @JoinColumn(name = "id_pagamento")
    private Pagamento pagamento;

    public Evento(Integer id, LocalDate data, Double preco, String avalicacao, Double nota, String status, String motivoNaoAceito, Buffet buffet, Pagamento pagamento, Boolean isFormularioDinamico) {
        this.id = id;
        this.data = data;
        this.preco = preco;
        this.avalicacao = avalicacao;
        this.nota = nota;
        this.status = status;
        this.isFormularioDinamico = isFormularioDinamico;
        this.motivoNaoAceito = motivoNaoAceito;
        this.buffet = buffet;
        this.pagamento = pagamento;
    }

    public Evento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getAvalicacao() {
        return avalicacao;
    }

    public void setAvalicacao(String avalicacao) {
        this.avalicacao = avalicacao;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getFormularioDinamico() {
        return isFormularioDinamico;
    }

    public void setFormularioDinamico(Boolean formularioDinamico) {
        isFormularioDinamico = formularioDinamico;
    }

    public String getMotivoNaoAceito() {
        return motivoNaoAceito;
    }

    public void setMotivoNaoAceito(String motivoNaoAceito) {
        this.motivoNaoAceito = motivoNaoAceito;
    }

    public Buffet getBuffet() {
        return buffet;
    }

    public void setBuffet(Buffet buffet) {
        this.buffet = buffet;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
