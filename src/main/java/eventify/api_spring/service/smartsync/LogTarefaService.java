package eventify.api_spring.service.smartsync;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.domain.smartsync.LogTarefa;
import eventify.api_spring.dto.smartsync.LogTarefaCriacaoDto;
import eventify.api_spring.dto.smartsync.LogTarefaDto;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.mapper.smartsync.LogTarefaMapper;
import eventify.api_spring.repository.AcaoRepository;
import eventify.api_spring.repository.FuncionarioRepository;
import eventify.api_spring.repository.LogTarefaRepository;
import eventify.api_spring.repository.TarefaRepository;
import eventify.api_spring.repository.UsuarioRepository;
import eventify.api_spring.service.usuario.UsuarioService;

@Service
public class LogTarefaService {
    @Autowired
    private LogTarefaRepository logTarefaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private AcaoRepository acaoRepository;

    public List<LogTarefaDto> listarLogTarefa() {
        List<LogTarefa> logsTarefa = logTarefaRepository.findAll();

        if (logsTarefa.isEmpty()) {
            throw new NoContentException("Não há logs de tarefas");
        }
        
        return logsTarefa.stream().map(LogTarefaMapper::toDto).collect(Collectors.toList());
    }

    public List<LogTarefaDto> listarLogTarefaIndividual(Integer idExecutor, Boolean isFuncionario) {
        List<LogTarefa> logsTarefa = logTarefaRepository.findAll();

        if (logsTarefa.isEmpty()) {
            throw new NoContentException("Não há logs de tarefas");
        }

        return logsTarefa.stream().map(LogTarefaMapper::toDto).collect(Collectors.toList());
    }

    public LogTarefa criarLogTarefa(LogTarefaCriacaoDto logTarefaDto) {
        LogTarefa logTarefa = new LogTarefa();

        if (logTarefaDto.getIdFuncionario() != null) {
            logTarefa.setFuncionario(funcionarioRepository.findById(logTarefaDto.getIdFuncionario()).get());
        } else if (logTarefaDto.getIdUsuario() != null) {
            logTarefa.setUsuario(usuarioRepository.findById(logTarefaDto.getIdUsuario()).get());
        } else {
            throw new NotFoundException("É necessário informar o id do usuário ou do funcionário");
        }

        logTarefa.setTarefa(tarefaRepository.findById(logTarefaDto.getIdTarefa()).get());
        logTarefa.setAcao(acaoRepository.findById(logTarefaDto.getIdAcao()).get());

        logTarefa.setValor(logTarefaDto.getValor());
        logTarefa.setDataCriacao(logTarefaDto.getDataCriacao());

        logTarefa = logTarefaRepository.save(logTarefa);

        return logTarefa;
    }
}
