package eventify.api_spring.mapper.buffet;

import eventify.api_spring.domain.agenda.Agenda;
import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.dto.agenda.AgendaDto;
import eventify.api_spring.dto.buffet.BuffetDtoResposta;

import java.util.ArrayList;
import java.util.List;

public class BuffetMapper {
    public static BuffetDtoResposta toDto(Buffet buffet) {
        BuffetDtoResposta dto = new BuffetDtoResposta();
        dto.setId(buffet.getId());
        dto.setNome(buffet.getNome());
        dto.setDescricao(buffet.getDescricao());
        dto.setPrecoMedioDiaria(buffet.getPrecoMedioDiaria());
        dto.setEndereco(buffet.getEndereco());
        dto.setTamanho(buffet.getTamanho());
        dto.setQtdPessoas(buffet.getQtdPessoas());
        dto.setCaminhoComprovante(buffet.getCaminhoComprovante());
        dto.setResidenciaComprovada(buffet.isResidenciaComprovada());
        dto.setFaixasEtarias(buffet.getFaixaEtarias());
        dto.setTiposEventos(buffet.getTiposEventos());
        dto.setServicos(buffet.getServicos());
        dto.setNomeProprietario(buffet.getUsuario().getNome());

        List<AgendaDto> agendas = new ArrayList<>();

        for (Agenda ag: buffet.getAgendas()) {
            agendas.add(new AgendaDto(ag.getId(), ag.getData()));
        }

        dto.setAgendas(agendas);
        dto.setImagens(buffet.getImagemDto());

        return dto;
    }
}
