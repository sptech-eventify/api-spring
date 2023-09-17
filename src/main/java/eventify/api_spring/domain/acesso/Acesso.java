package eventify.api_spring.domain.acesso;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime dataCriacao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_pagina")
    private Pagina pagina;

    public Acesso(LocalDateTime data, Pagina Pagina) {
        this.dataCriacao = data;
        this.pagina = Pagina;
    }
}
