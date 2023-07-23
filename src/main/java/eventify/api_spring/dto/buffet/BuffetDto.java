package eventify.api_spring.dto.buffet;

import eventify.api_spring.domain.agenda.Agenda;
import eventify.api_spring.domain.buffet.FaixaEtaria;
import eventify.api_spring.domain.buffet.Servico;
import eventify.api_spring.domain.buffet.TipoEvento;
import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.dto.imagem.ImagemDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class BuffetDto {
    private Integer id;
    private String nome;
    private String descricao;
    private String tamanho;
    private Double precoMedioDiaria;
    private Integer qtdPessoas;
    private String caminhoComprovante;
    private boolean isVisivel;
    private LocalDate dataCriacao;
    private Endereco endereco;
    private Set<FaixaEtaria> faixaEtarias;
    private Set<TipoEvento> tipoEventos;
    private Set<Servico> servicos;
    private List<ImagemDTO> imagens;
    private List<Agenda> agendas;
}
