package eventify.sptech.apispring.Entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate data;
    private Double preco;
    private String avalicacao;
    private Double nota;
    private String status;
    private String motivoNaoAceito;
    @ManyToOne
    private Buffet buffet;
    @OneToOne
    private Pagamento pagamento;

    public Evento(Integer id, LocalDate data, Double preco, String avalicacao, Double nota, String status, String motivoNaoAceito, Buffet buffet, Pagamento pagamento) {
        this.id = id;
        this.data = data;
        this.preco = preco;
        this.avalicacao = avalicacao;
        this.nota = nota;
        this.status = status;
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
