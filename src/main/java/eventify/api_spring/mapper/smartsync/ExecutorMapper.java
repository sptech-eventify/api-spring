package eventify.api_spring.mapper.smartsync;

import eventify.api_spring.domain.smartsync.ExecutorTarefa;
import eventify.api_spring.dto.smartsync.ExecutorDto;

public class ExecutorMapper {
    public static ExecutorDto toDto(ExecutorTarefa domain) {
        ExecutorDto dto = new ExecutorDto();

        dto.setId(domain.getId());
        dto.setTempoExecutado(domain.getTempoExecutado());
        
        if (domain.getFuncionario() != null) {
            dto.setIdFuncionario(domain.getFuncionario().getId());
            dto.setUrlFoto(domain.getFuncionario().getImagem());
            dto.setNome(domain.getFuncionario().getNome());
        } else {
            dto.setIdUsuario(domain.getUsuario().getId());
            dto.setUrlFoto(domain.getUsuario().getImagem());
            dto.setNome(domain.getUsuario().getNome());
        }

        return dto;
    }
}
