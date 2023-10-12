package eventify.api_spring.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 128)
    @NotBlank
    private String nome;

    @Size(min = 11, max = 11)
    @NotBlank
    private String cpf;

    @Size(max = 256)
    @NotBlank
    private String email;

    @Size(min = 14, max = 14)
    @NotBlank
    private String telefone;

    @DecimalMin("0.0")
    @DecimalMax("999999.99")
    @NotNull
    private Double salario;

    @NotNull
    private Boolean isVisivel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_nivel_acesso")
    private Integer idNivelAcesso;
}
