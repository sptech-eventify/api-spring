package eventify.api_spring.dto.smartsync;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaSecaoDto {
    private Integer idBuffet;
    private Integer idBuffetServico;
    private Integer idEvento;
    private Integer id;
    private String nome;
    private String descricao;
    private Integer fibonacci;
    private Integer status;
    private Integer horasEstimada;
    private Date dataEstimada;
    private Timestamp dataCriacao;
    private Timestamp dataConclusao;
    private Byte isVisivel;
    private Integer idTarefa;
    private Integer idBucket;
    private List<ComentarioRespostaDto> comentarios;
    private List<ExecutorDto> responsaveis;
}
