package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.smartsync.ExecutorTarefa;
import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.smartsync.ExecutorDto;
import eventify.api_spring.dto.smartsync.ExecutorTarefaCriacaoDto;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.repository.ExecutorTarefaRepository;
import eventify.api_spring.repository.FuncionarioRepository;
import eventify.api_spring.repository.TarefaRepository;
import eventify.api_spring.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExecutorTarefaService {
    @Autowired
    private ExecutorTarefaRepository executorTarefaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BuffetRepository buffetRepository;

    public List<ExecutorDto> exibirTodosExecutoresTarefas() {
        List<ExecutorTarefa> executorTarefas = executorTarefaRepository.findAll();

        List<ExecutorDto> executores = new ArrayList();

        for (ExecutorTarefa executorTarefa : executorTarefas) {
            ExecutorDto executorDto = new ExecutorDto();
            executorDto.setId(executorTarefa.getId());
            executorDto.setTempoExecutado(executorTarefa.getTempoExecutado());

            if (executorTarefa.getFuncionario() != null) {
                executorDto.setId(executorTarefa.getFuncionario().getId());
                executorDto.setNome(executorTarefa.getFuncionario().getNome());
                executorDto.setUrlFoto(executorTarefa.getFuncionario().getImagem());
                executorDto.setIdFuncionario(executorTarefa.getFuncionario().getId());
            } else {
                executorDto.setId(executorTarefa.getUsuario().getId());
                executorDto.setNome(executorTarefa.getUsuario().getNome());
                executorDto.setUrlFoto(executorTarefa.getUsuario().getImagem());
                executorDto.setIdUsuario(executorTarefa.getUsuario().getId());
            }

            executores.add(executorDto);
        }

        return executores;
    }

    public List<ExecutorDto> executoresPorIdTarefa(Integer idTarefa) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(idTarefa);
        
        if (tarefa.isEmpty()){
            throw new NotFoundException("Tarefa não encontrada");
        }

        List<ExecutorTarefa> executorTarefas = executorTarefaRepository.findAllByTarefa(tarefa.get());

        List<ExecutorDto> executores = new ArrayList();

        for (ExecutorTarefa executorTarefa : executorTarefas) {
            ExecutorDto executorDto = new ExecutorDto();
            executorDto.setId(executorTarefa.getId());
            executorDto.setTempoExecutado(executorTarefa.getTempoExecutado());

            if (executorTarefa.getFuncionario() != null) {
                executorDto.setId(executorTarefa.getFuncionario().getId());
                executorDto.setNome(executorTarefa.getFuncionario().getNome());
                executorDto.setUrlFoto(executorTarefa.getFuncionario().getImagem());
                executorDto.setIdFuncionario(executorTarefa.getFuncionario().getId());
            } else {
                executorDto.setId(executorTarefa.getUsuario().getId());
                executorDto.setNome(executorTarefa.getUsuario().getNome());
                executorDto.setUrlFoto(executorTarefa.getUsuario().getImagem());
                executorDto.setIdUsuario(executorTarefa.getUsuario().getId());
            }

            executores.add(executorDto);
        }

        return executores;
    }

    public List<ExecutorDto> executoresDisponiveis(Integer idBuffet) {
        Buffet buffet = buffetRepository.findById(idBuffet).orElseThrow(() -> new NotFoundException("Buffet não encontrado"));
        List<Funcionario> funcionarios = funcionarioRepository.findAllByEmpregador(buffet.getUsuario());
        List<ExecutorDto> executorTarefas = new ArrayList();

        for (Funcionario funcionario : funcionarios) {
            ExecutorDto executorDto = new ExecutorDto();
            executorDto.setId(funcionario.getId());
            executorDto.setNome(funcionario.getNome());
            executorDto.setUrlFoto(funcionario.getImagem());
            executorDto.setIdFuncionario(funcionario.getId());

            executorTarefas.add(executorDto);
        }

        Usuario usuario = buffet.getUsuario();
        executorTarefas.add(new ExecutorDto(usuario.getId(), usuario.getNome(), usuario.getImagem(), null, usuario.getId(), null));

        return executorTarefas;
    }

    public ExecutorDto adicionarExecutorTarefa(ExecutorTarefaCriacaoDto novoExecutor) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(novoExecutor.getIdTarefa());

        if (tarefa.isEmpty()){
            throw new NotFoundException("Tarefa não encontrada");
        }

        ExecutorTarefa executorTarefa = new ExecutorTarefa();
        executorTarefa.setTempoExecutado(novoExecutor.getTempoExecutado());
        executorTarefa.setDataCriacao(novoExecutor.getDataCriacao());
        executorTarefa.setTarefa(tarefa.get());

        if (novoExecutor.getIdFuncionario() != null) {
            executorTarefa.setFuncionario(funcionarioRepository.findById(novoExecutor.getIdFuncionario()).get());
        } else {
            executorTarefa.setUsuario(usuarioRepository.findById(novoExecutor.getIdUsuario()).get());
        }

        ExecutorTarefa salvo = executorTarefaRepository.save(executorTarefa);

        ExecutorDto executorDto = new ExecutorDto();
        executorDto.setId(salvo.getId());
        executorDto.setTempoExecutado(salvo.getTempoExecutado());

        if (salvo.getFuncionario() != null) {
            executorDto.setId(salvo.getFuncionario().getId());
            executorDto.setNome(salvo.getFuncionario().getNome());
            executorDto.setUrlFoto(salvo.getFuncionario().getImagem());
            executorDto.setIdFuncionario(salvo.getFuncionario().getId());
        } else {
            executorDto.setId(salvo.getUsuario().getId());
            executorDto.setNome(salvo.getUsuario().getNome());
            executorDto.setUrlFoto(salvo.getUsuario().getImagem());
            executorDto.setIdUsuario(salvo.getUsuario().getId());
        }

        return executorDto;
    }

    public ExecutorTarefaCriacaoDto atualizarExecutorTarefa(Integer id, ExecutorTarefaCriacaoDto executorTarefaAtualizado) {
        Optional<ExecutorTarefa> executorTarefa = executorTarefaRepository.findById(id);

        if (executorTarefa.isEmpty()){
            throw new NotFoundException("Executor não encontrado");
        }

        executorTarefa.get().setTempoExecutado(executorTarefaAtualizado.getTempoExecutado());
        executorTarefa.get().setDataCriacao(executorTarefaAtualizado.getDataCriacao());

        ExecutorTarefa executorTarefaSalvo = executorTarefaRepository.save(executorTarefa.get());

        ExecutorTarefaCriacaoDto executorTarefaCriacaoDto = new ExecutorTarefaCriacaoDto();
        executorTarefaCriacaoDto.setTempoExecutado(executorTarefaSalvo.getTempoExecutado());
        executorTarefaCriacaoDto.setDataCriacao(executorTarefaSalvo.getDataCriacao());
        executorTarefaCriacaoDto.setIdTarefa(executorTarefaSalvo.getTarefa().getId());

        if (executorTarefaSalvo.getFuncionario() != null) {
            executorTarefaCriacaoDto.setIdFuncionario(executorTarefaSalvo.getFuncionario().getId());
        } else {
            executorTarefaCriacaoDto.setIdUsuario(executorTarefaSalvo.getUsuario().getId());
        }

        return executorTarefaCriacaoDto;
    }

    public void removerExecutorTarefa(Integer idExecutorTarefa) {
        Optional<ExecutorTarefa> executorTarefa = executorTarefaRepository.findById(idExecutorTarefa);

        if (executorTarefa.isEmpty()){
            throw new NotFoundException("Executor não encontrado");
        }

        executorTarefaRepository.delete(executorTarefa.get());
    }
}
