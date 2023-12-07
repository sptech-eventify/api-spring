package eventify.api_spring.dto.smartsync.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoCanceladoDto {
    private Integer id;
    private String nome;
    private Long quantidadeEventosRecusados;
    private Long quantidadeEventosConfirmados;
}
