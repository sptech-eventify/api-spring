package eventify.api_spring.domain.buffet;
import jakarta.persistence.*;

import java.time.LocalDate;

import eventify.api_spring.domain.usuario.Usuario;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;
    private LocalDate dataCriacao;

    @ManyToOne
    private Usuario usuario;
}