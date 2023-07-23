package eventify.api_spring.domain.evento;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.pagamento.Pagamento;
import eventify.api_spring.domain.usuario.Usuario;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDate data;

    @NotNull
    @DecimalMin("0.01")
    private Double preco;

    private String avaliacao;
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

    @ManyToOne
    @JoinColumn(name = "id_contratante")
    private Usuario contratante;

    public Evento(Integer id, LocalDate data, Double preco, String avalicacao, Double nota, String status, String motivoNaoAceito, Buffet buffet, Pagamento pagamento, Boolean isFormularioDinamico, Usuario contratante) {
        this.id = id;
        this.data = data;
        this.preco = preco;
        this.avaliacao = avalicacao;
        this.nota = nota;
        this.status = status;
        this.isFormularioDinamico = isFormularioDinamico;
        this.motivoNaoAceito = motivoNaoAceito;
        this.buffet = buffet;
        this.pagamento = pagamento;
        this.contratante = contratante;
    }
}
