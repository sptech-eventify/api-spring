package eventify.api_spring.dto.smartsync;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDto {
    private Integer id;
    private String nome;
    private String descricao;
    private Integer fibonacci;
    private Integer status;
    private Integer horasEstimada;
    private LocalDate dataEstimada;
    private LocalDateTime dataConclusao;
    private LocalDate dataCriacao;
    private Boolean isVisivel;
    private Integer idTarefaPai;
    private Integer idBucket;
    private Integer idServico;
    private List<ComentarioRespostaDto> comentarios;
    private List<ExecutorDto> responsaveis;
}