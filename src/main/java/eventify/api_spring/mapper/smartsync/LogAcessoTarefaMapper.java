package eventify.api_spring.mapper.smartsync;

import eventify.api_spring.domain.smartsync.LogAcessoTarefa;
import eventify.api_spring.dto.smartsync.LogAcessoTarefaDto;

public class LogAcessoTarefaMapper {
    public static LogAcessoTarefaDto toDto(LogAcessoTarefa logAcessoTarefa) {
        LogAcessoTarefaDto logAcessoTarefaDto = new LogAcessoTarefaDto();

        logAcessoTarefaDto.setId(logAcessoTarefa.getId());
        logAcessoTarefaDto.setDataCriacao(logAcessoTarefa.getDataCriacao());
        logAcessoTarefaDto.setTarefa(TarefaMapper.toDto(logAcessoTarefa.getTarefa()));

        return logAcessoTarefaDto;
    }   
}
