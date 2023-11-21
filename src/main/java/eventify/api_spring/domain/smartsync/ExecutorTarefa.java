package eventify.api_spring.domain.smartsync;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.Usuario;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "executor_tarefa")
public class ExecutorTarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer tempoExecutado;
    private LocalDateTime dataCriacao;
    private Boolean isRemovido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarefa")
    private Tarefa tarefa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_executor_funcionario")
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_executor_usuario")
    private Usuario usuario;
}