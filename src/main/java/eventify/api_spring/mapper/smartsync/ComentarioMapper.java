package eventify.api_spring.mapper.smartsync;

import eventify.api_spring.domain.smartsync.Comentario;
import eventify.api_spring.dto.smartsync.ComentarioRespostaDto;

public class ComentarioMapper {
    public static ComentarioRespostaDto toDto(Comentario domain) {
        ComentarioRespostaDto dto = new ComentarioRespostaDto();

        dto.setId(domain.getId());
        dto.setMensagem(domain.getMensagem());
        dto.setDataCriacao(domain.getDataCriacao());
        dto.setIsVisivel(domain.getIsVisivel());

        ComentarioRespostaDto.Remetente remetente = dto.new Remetente();

        if (domain.getFuncionario() != null) {
            remetente.setId(domain.getFuncionario().getId());
            remetente.setNome(domain.getFuncionario().getNome());
            remetente.setFoto(domain.getFuncionario().getImagem());
            remetente.setIsFuncionario(true);
        } else {
            remetente.setId(domain.getUsuario().getId());
            remetente.setNome(domain.getUsuario().getNome());
            remetente.setFoto(domain.getUsuario().getImagem());
            remetente.setIsFuncionario(false);
        }

        return dto;
    }
}
