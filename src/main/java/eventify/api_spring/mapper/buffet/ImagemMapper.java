package eventify.api_spring.mapper.buffet;

import org.mapstruct.Mapper;

import eventify.api_spring.domain.buffet.Imagem;
import eventify.api_spring.dto.imagem.ImagemDto;

@Mapper(componentModel = "spring")
public interface ImagemMapper {
    ImagemDto toDto(Imagem domain);
}