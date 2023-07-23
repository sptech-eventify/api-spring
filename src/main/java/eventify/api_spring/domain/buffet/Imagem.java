package eventify.api_spring.domain.buffet;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "assets/files/img/buffet1/img1.png")
    private String caminho;

    @Schema(example = "img1")
    private String nome;

    @Schema(example = "png")
    private String tipo;

    @Schema(example = "true")
    private boolean isAtivo;

    @Schema(example = "2023-04-25T11:46:23.137Z")
    private LocalDateTime dataUpload;

    @ManyToOne()
    @JoinColumn(name = "id_buffet")
    private Buffet buffet;
}
