package eventify.api_spring.dto.orcamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrcamentoContratanteDto {
    private Object caminho;
    private Object nome;
    private Object tipo;
    private Object buffet_nome;
    private Object descricao;
    private Object data;
    private Object preco;
    private Object status;
    private Object id_contratante;
}
