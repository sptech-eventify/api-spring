package eventify.api_spring.domain.smartsync;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.Usuario;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 512)
    @NotBlank
    private String mensagem;

    @NotNull
    private LocalDateTime dataCriacao;

    @NotNull
    private Boolean isVisivel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tarefa")
    private Tarefa tarefa;
}
