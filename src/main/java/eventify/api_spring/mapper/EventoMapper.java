package eventify.api_spring.mapper;

import eventify.api_spring.domain.Evento;
import eventify.api_spring.dto.EventoCriacaoDto;

public class EventoMapper {

    public static Evento of(EventoCriacaoDto eventoCriacaoDto) {
        Evento evento = new Evento();
        evento.setStatus("1");
        evento.setPreco(eventoCriacaoDto.preco());
        evento.setBuffet(eventoCriacaoDto.buffet());
        evento.setData(eventoCriacaoDto.data());
        evento.setContratante(eventoCriacaoDto.contratante());
        evento.setFormularioDinamico(eventoCriacaoDto.isFormularioDinamico());
        return evento;
    }

}
