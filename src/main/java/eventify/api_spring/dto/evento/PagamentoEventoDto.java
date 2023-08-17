package eventify.api_spring.dto.evento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoEventoDto {
    private Object idEvento;
    private Object data;
    private Object preco;
    private Object isPagoBuffet;
    private Object idBuffet;
    private Object idContratante;
}