package eventify.api_spring.dto.buffet;
import eventify.api_spring.domain.buffet.Servico;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuffetSmartSyncResumoDto {
    private Integer id;
    private String nome;
    private Integer capacidade;
    private Integer tamanho;
    private String rua;
    private String bairro;
    private String numero;
    private List<Servico> servicos;
}
