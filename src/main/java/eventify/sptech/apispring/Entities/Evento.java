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
    @ManyToOne
    private Buffet buffet;
    @OneToOne
    private Contratante contratante;

    public Evento(Integer id, LocalDate data, Double preco, String avalicacao, Double nota, Buffet buffet, Contratante contratante) {
        this.id = id;
        this.data = data;
        this.preco = preco;
        this.avalicacao = avalicacao;
        this.nota = nota;
        this.buffet = buffet;
        this.contratante = contratante;
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

    public Buffet getBuffet() {
        return buffet;
    }

    public void setBuffet(Buffet buffet) {
        this.buffet = buffet;
    }

    public Contratante getContratante() {
        return contratante;
    }

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }
}
