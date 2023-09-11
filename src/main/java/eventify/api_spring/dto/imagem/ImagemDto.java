package eventify.api_spring.dto.imagem;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImagemDto {
    private Integer id;
    private String caminho;
    private String nome;
    private String tipo;
    private boolean isAtivo;
    private LocalDateTime dataUpload;
}
