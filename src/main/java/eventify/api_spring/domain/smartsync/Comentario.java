package eventify.api_spring.domain.smartsync;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @JoinColumn(name = "id_funcionario_gestor")
    private Integer idFuncionarioGestor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_funcionario")
    private Integer idFuncionario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Integer idUsuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tarefa")
    private Integer idTarefa;
}
