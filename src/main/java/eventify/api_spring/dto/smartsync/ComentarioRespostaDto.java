package eventify.api_spring.dto.smartsync;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioRespostaDto {
    private Integer id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean isVisivel;
    private Remetente remetente;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Remetente {
        private Integer id;
        private String nome;
        private String foto;
        private Boolean isFuncionario;
    }
}
