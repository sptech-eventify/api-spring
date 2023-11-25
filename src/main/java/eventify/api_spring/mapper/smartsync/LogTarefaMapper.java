package eventify.api_spring.mapper.smartsync;

import eventify.api_spring.domain.smartsync.LogTarefa;
import eventify.api_spring.dto.smartsync.LogTarefaDto;
import eventify.api_spring.mapper.usuario.FuncionarioMapper;
import eventify.api_spring.mapper.usuario.UsuarioMapper;

public class LogTarefaMapper {
    public static LogTarefaDto toDto(LogTarefa logTarefa) {
        LogTarefaDto logTarefaDto = new LogTarefaDto();
        logTarefaDto.setId(logTarefa.getId());
        logTarefaDto.setValor(logTarefa.getValor());
        logTarefaDto.setDataCriacao(logTarefa.getDataCriacao());
        logTarefaDto.setTarefa(TarefaMapper.toDto(logTarefa.getTarefa()));

        if (logTarefa.getUsuario() != null) {
            logTarefaDto.setUsuario(UsuarioMapper.toDevolverDto(logTarefa.getUsuario()));
        } else {
            logTarefaDto.setFuncionario(FuncionarioMapper.toDevolverDto(logTarefa.getFuncionario()));
        }

        logTarefaDto.setAcao(logTarefa.getAcao());
        logTarefaDto.setServico(logTarefa.getTarefa().getBucket().getBuffetServico().getServico());

        return logTarefaDto;
    }
}
