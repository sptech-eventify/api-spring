package eventify.api_spring.dto.smartsync;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImpressaoDto {
    private List<AcessoDto> acessos;
    private Double indiceAcessos;
    private List<VisualizacaoDto> visualizacoes;
    private Double indiceVisualizacoes;
    private List<eventify.api_spring.dto.smartsync.AvaliacaoDto> avaliacoes;
    private Double indiceAvaliacoes;

    public ImpressaoDto(List<AcessoDto> acessos, List<VisualizacaoDto> visualizacoes, List<eventify.api_spring.dto.smartsync.AvaliacaoDto> avaliacoesDto) {
        this.acessos = acessos;
        this.visualizacoes = visualizacoes;
        this.avaliacoes = avaliacoesDto;
    }
}