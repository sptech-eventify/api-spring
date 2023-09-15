package eventify.api_spring.dto.buffet;

import java.util.List;

import eventify.api_spring.domain.buffet.TipoEvento;
import eventify.api_spring.dto.imagem.ImagemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuffetConsultaDto{
    private Integer id;
    private List<TipoEvento> tiposEventos;
    private String nome;
    private Double precoMedioDiaria; 
    private Double notaMediaAvaliacao;
    private List<ImagemDto> imagens;
}