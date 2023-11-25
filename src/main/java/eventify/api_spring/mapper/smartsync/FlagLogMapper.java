package eventify.api_spring.mapper.smartsync;

import java.sql.Timestamp;

import eventify.api_spring.domain.smartsync.FlagLog;
import eventify.api_spring.dto.smartsync.FlagLogDto;

public class FlagLogMapper {
    public static FlagLogDto toDto(FlagLog domain) {
        FlagLogDto dto = new FlagLogDto();

        dto.setId(domain.getId());
        dto.setDataCriacao(Timestamp.valueOf(domain.getDataCriacao()));
        dto.setTarefa(TarefaMapper.toDto(domain.getTarefa()));

        return dto;
    }
}
