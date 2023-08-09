package eventify.api_spring.mapper.evento;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.evento.Evento;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.evento.EventoCriacaoDto;
import eventify.api_spring.dto.evento.EventoDto;

public class EventoMapper {
    public static Evento of(EventoCriacaoDto eventoCriacaoDto, Buffet buffet, Usuario usuario) {
        Evento evento = new Evento();
        evento.setStatus("1");
        evento.setPreco(eventoCriacaoDto.getPreco());
        evento.setBuffet(buffet);
        evento.setData(eventoCriacaoDto.getData());
        evento.setContratante(usuario);
        evento.setIsFormularioDinamico(eventoCriacaoDto.getIsFormularioDinamico());

        return evento;
    }

    public static EventoDto toDto(Evento domain) {
        EventoDto dto = new EventoDto();
        dto.setId(domain.getId());
        dto.setNome(domain.getBuffet().getNome());
        dto.setData(domain.getData());
        dto.setPreco(domain.getPreco());
        dto.setNota(domain.getNota());
        dto.setDescricao(domain.getBuffet().getDescricao());
        dto.setCaminho(domain.getBuffet().getImagens().get(0).getCaminho());
        dto.setStatus(domain.getStatus());

        return dto;
    }
}
