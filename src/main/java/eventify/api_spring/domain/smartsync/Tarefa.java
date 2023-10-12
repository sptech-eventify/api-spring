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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 64)
    private String nome;

    @NotBlank
    @Size(max = 256)
    private String descricao;

    @NotNull
    private Integer fibonacci;

    @NotNull
    private Integer status;

    @NotNull
    private Integer horasEstimada;

    @NotNull
    private LocalDate dataEstimada;

    @NotNull
    private LocalDate dataCriacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "id_bucket")
    private Integer idBucket;
}
