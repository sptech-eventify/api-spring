package eventify.api_spring.mapper.endereco;

import org.mapstruct.Mapper;

import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.dto.endereco.EnderecoRespostaDto;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
  EnderecoRespostaDto toDto(Endereco endereco);
}