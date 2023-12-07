package eventify.api_spring.service.smartsync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversaoVisitasDto {
    private Integer id;
    private String nome;
    private Long quantidadeEventos;
    private Long quantidadeAcessos;
}
