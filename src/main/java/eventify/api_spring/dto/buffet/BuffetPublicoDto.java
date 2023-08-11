package eventify.api_spring.dto.buffet;

import java.util.List;

import eventify.api_spring.domain.buffet.FaixaEtaria;
import eventify.api_spring.domain.buffet.Servico;
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
public class BuffetPublicoDto {
        private String nome;
        private String nomeProprietario;
        private String descricao;
        private List<ImagemDto> imagens;
        private Double precoMedioDiaria;
        private Double notaMediaAvaliacao;
        private List<Servico> servicos;
        private List<FaixaEtaria> faixasEtarias;
        private List<TipoEvento> tiposEventos;
        private Double latitude;
        private Double longitude;
        private String logradouro;
        private String numero;
        private String bairro;
        private String cidade;
        private String uf;
        private String cep;
}