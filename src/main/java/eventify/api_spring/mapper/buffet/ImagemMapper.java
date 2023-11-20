package eventify.api_spring.mapper.buffet;

import eventify.api_spring.domain.buffet.Imagem;
import eventify.api_spring.dto.imagem.ImagemDto;

public class ImagemMapper {
    public static ImagemDto toDto(Imagem domain) {
        ImagemDto dto = new ImagemDto();

        dto.setId(domain.getId());
        dto.setNome(domain.getNome());
        dto.setCaminho(domain.getCaminho());
        dto.setTipo(domain.getTipo());
        dto.setAtivo(domain.isAtivo());
        dto.setDataUpload(domain.getDataUpload());

        return dto;
    }
}