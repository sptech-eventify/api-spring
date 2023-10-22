package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.smartsync.ExecutorTarefa;
import eventify.api_spring.repository.ExecutorTarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutorTarefaService {

    @Autowired
    private ExecutorTarefaRepository executorTarefaRepository;

    public List<ExecutorTarefa> exibirTodosExecutoresTarefas() {
        List<ExecutorTarefa> executorTarefas = executorTarefaRepository.findAll();

        return executorTarefas;
    }

    public ExecutorTarefa exibirExecutorTarefaPorId(Integer id) {
        ExecutorTarefa executorTarefa = executorTarefaRepository.findById(id).orElseThrow(() -> new RuntimeException("ExecutorTarefa não encontrado"));

        return executorTarefa;
    }


    public List<ExecutorTarefa> exibirTodosExecutoresTarefasPorFuncionarioId(Integer idFuncionario) {
        List<ExecutorTarefa> executorTarefas = executorTarefaRepository.findAllByFuncionarioId(idFuncionario);

        return executorTarefas;
    }

    public ExecutorTarefa criarExecutorTarefa(ExecutorTarefa executorTarefa) {
        return executorTarefaRepository.save(executorTarefa);
    }

    public ExecutorTarefa atualizarExecutorTarefa(Integer id) {
        ExecutorTarefa executorTarefa = executorTarefaRepository.findById(id).orElseThrow(() -> new RuntimeException("ExecutorTarefa não encontrado"));

        return executorTarefaRepository.save(executorTarefa);
    }

    public void deletarExecutorTarefa(Integer id) {
        executorTarefaRepository.deleteById(id);
    }
}
