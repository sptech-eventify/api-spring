package eventify.api_spring.dto.smartsync;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BucketTarefaDto {
    private Integer idBucket;
    private String nomeBucket;
    private List<TarefaDto> tarefas;
}
