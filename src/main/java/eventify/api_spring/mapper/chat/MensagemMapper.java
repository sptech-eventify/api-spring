package eventify.api_spring.mapper.chat;

import eventify.api_spring.domain.chat.Mensagem;
import eventify.api_spring.dto.chat.MensagemDto;

public class MensagemMapper {
    public static MensagemDto toDto(Mensagem domain) {
        MensagemDto dto = new MensagemDto();
        dto.setId(domain.getId());
        dto.setMensagem(domain.getMensagem());
        dto.setMandadoPor(domain.isMandadoPor());
        dto.setData(domain.getData());
        dto.setIdUsuario(domain.getUsuario().getId());
        dto.setIdBuffet(domain.getBuffet().getId());
        
        return dto;
    }
}