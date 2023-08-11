package eventify.api_spring.mapper.agenda;

import eventify.api_spring.domain.agenda.Agenda;
import eventify.api_spring.dto.agenda.AgendaCriacaoDto;
import eventify.api_spring.dto.agenda.AgendaDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgendaMapper {
  @Mapping(source = "buffet.id", target = "idBuffet")
  AgendaDto toDto(Agenda domain);

  @Mapping(source = "idBuffet", target = "buffet.id")
  Agenda toDomain(AgendaCriacaoDto dto);
}