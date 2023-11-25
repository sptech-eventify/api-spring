package eventify.api_spring.service.smartsync;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.domain.smartsync.LogAcessoTarefa;
import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.smartsync.LogAcessoTarefaCriacaoDto;
import eventify.api_spring.dto.smartsync.LogAcessoTarefaDto;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.mapper.smartsync.LogAcessoTarefaMapper;
import eventify.api_spring.repository.FuncionarioRepository;
import eventify.api_spring.repository.LogAcessoTarefaRepository;
import eventify.api_spring.repository.TarefaRepository;
import eventify.api_spring.repository.UsuarioRepository;

@Service
public class LogAcessoTarefaService {
    @Autowired
    private LogAcessoTarefaRepository logAcessoTarefaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<LogAcessoTarefaDto> retornarLogAcessoTarefa(Integer idExecutor, Boolean isFuncionario) {
        Funcionario funcionario = null;
        Usuario usuario = null;

        if (isFuncionario) {
            funcionario = funcionarioRepository.findById(idExecutor).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
        } else {
            usuario = usuarioRepository.findById(idExecutor).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        }
    
        List<LogAcessoTarefa> logAcessoTarefa = new ArrayList<>();

        if (isFuncionario) {
            logAcessoTarefa = logAcessoTarefaRepository.findAllByFuncionario(funcionario);
        } else {
            logAcessoTarefa = logAcessoTarefaRepository.findAllByUsuario(usuario);
        }
        
        if (logAcessoTarefa.isEmpty()) {
            throw new NoContentException("Log de acesso não encontrado");
        }

        return logAcessoTarefa.stream().map(LogAcessoTarefaMapper::toDto).collect(Collectors.toList());
    }

    public LogAcessoTarefa salvarLogAcessoTarefa(LogAcessoTarefaCriacaoDto logAcessoTarefaDto) {
        LogAcessoTarefa logAcessoTarefa = new LogAcessoTarefa();

        Tarefa tarefa = tarefaRepository.findById(logAcessoTarefaDto.getIdTarefa()).orElseThrow(() -> new NotFoundException("Tarefa não encontrada"));
        
        logAcessoTarefa.setTarefa(tarefa);
        logAcessoTarefa.setDataCriacao(logAcessoTarefaDto.getDataCriacao());
        
        if (logAcessoTarefaDto.getIdFuncionario() != null) {
            Funcionario funcionario = funcionarioRepository.findById(logAcessoTarefaDto.getIdFuncionario()).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
            logAcessoTarefa.setFuncionario(funcionario);

            return logAcessoTarefaRepository.save(logAcessoTarefa);
        } else if (logAcessoTarefaDto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(logAcessoTarefaDto.getIdUsuario()).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
            logAcessoTarefa.setUsuario(usuario);

            return logAcessoTarefaRepository.save(logAcessoTarefa);
        } else {
            throw new NotFoundException("Funcionário ou usuário não encontrado");
        }
    }
}
