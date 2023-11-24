package eventify.api_spring.dto.smartsync;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecaoBucketDto {
    private Integer idSecao;
    private String nomeSecao;
    private List<BucketTarefaDto> buckets;
}
