package eventify.api_spring.dto.buffet;
import eventify.api_spring.domain.buffet.FaixaEtaria;
import eventify.api_spring.domain.buffet.Servico;
import eventify.api_spring.domain.buffet.TipoEvento;
import eventify.api_spring.dto.agenda.AgendaDto;
import eventify.api_spring.dto.endereco.EnderecoRespostaDto;
import eventify.api_spring.dto.imagem.ImagemDto;
import eventify.api_spring.dto.usuario.UsuarioDevolverDto;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuffetRespostaDto {
    private Integer id;
    private String nome;
    private String descricao;
    private Double precoMedioDiaria;
    private EnderecoRespostaDto endereco;
    private Integer tamanho;
    private Integer qtdPessoas;
    private String caminhoComprovante;
    private Boolean residenciaComprovada;
    private Set<FaixaEtaria> faixasEtarias;
    private Set<TipoEvento> tiposEventos;
    private Set<Servico> servicos;
    private UsuarioDevolverDto usuario;
    private List<AgendaDto> agendas;
    private List<ImagemDto> imagens;
}