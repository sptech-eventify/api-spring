package eventify.api_spring.domain.buffet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @DecimalMin("0.0")
    private Double valor;

    @NotBlank
    @Size(max = 64)
    private String referente;

    @NotBlank
    private LocalDate dataCriacao;

    @NotBlank
    private boolean isGasto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "id_buffet")
    private Buffet buffet;

}
