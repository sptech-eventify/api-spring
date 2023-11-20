package eventify.api_spring.mapper.agenda;

import eventify.api_spring.domain.agenda.Agenda;
import eventify.api_spring.dto.agenda.AgendaCriacaoDto;
import eventify.api_spring.dto.agenda.AgendaDto;

public interface AgendaMapper {
  public static AgendaDto toDto(Agenda domain) {
    AgendaDto dto = new AgendaDto();

    dto.setId(domain.getId());
    dto.setData(domain.getData());
    dto.setIdBuffet(domain.getBuffet().getId());

    return dto;
  }

  public static Agenda toDomain(AgendaCriacaoDto dto) {
    Agenda domain = new Agenda();
    
    domain.setData(dto.getData());

    return domain;
  }
}