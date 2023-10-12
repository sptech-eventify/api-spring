package eventify.api_spring.domain.smartsync;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class ExecutorTarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "id_usuario")
    private Integer id;

    @NotBlank
    private Instant  tempoExecutado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "id_funcionario")
    private Integer idExecutorFuncionario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "id_usuario")
    private Integer idExecutorUsuario;

}
