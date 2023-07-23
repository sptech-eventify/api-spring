package eventify.api_spring.domain.acesso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

import eventify.api_spring.domain.agenda.Pagina;

@Getter
@Setter
@AllArgsConstructor
public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_pagina")
    private Pagina pagina;
}
