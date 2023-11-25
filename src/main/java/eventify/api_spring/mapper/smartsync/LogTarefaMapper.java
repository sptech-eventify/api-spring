package eventify.api_spring.mapper.smartsync;

import eventify.api_spring.domain.buffet.Servico;
import eventify.api_spring.domain.smartsync.LogTarefa;
import eventify.api_spring.dto.buffet.ServicoDto;
import eventify.api_spring.dto.smartsync.AcaoDto;
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

        logTarefaDto.setAcao(new AcaoDto(logTarefa.getAcao().getId(), logTarefa.getAcao().getDescricao()));
        
        Servico servico = logTarefa.getTarefa().getBucket().getBuffetServico().getServico();
        logTarefaDto.setServico(new ServicoDto(servico.getId(), servico.getDescricao()));

        return logTarefaDto;
    }
}
