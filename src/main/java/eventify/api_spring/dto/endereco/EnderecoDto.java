package eventify.api_spring.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto {
    private Object id;
    private Object isValidado;
    private Object logradouro;
    private Object numero;
    private Object bairro;
    private Object cidade;
    private Object uf;
    private Object cep;
    private Object latitude;
    private Object longitude;
    private Object dataCriacao;
}