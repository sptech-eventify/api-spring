package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> exibirTodasTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();

        if (tarefas.isEmpty()) {
            throw new NoContentException("Não há tarefas cadastradas");
        }

        return tarefas;
    }

    public Tarefa exibirTarefaPorId(Integer id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new NoContentException("Tarefa não encontrada"));

        return tarefa;
    }

    public List<Tarefa> exibirTodasTarefasPorBucketId(Integer idBucket) {
        List<Tarefa> tarefas = tarefaRepository.findAllByBucketId(idBucket);

        if (tarefas.isEmpty()) {
            throw new NoContentException("Não há tarefas cadastradas");
        }

        return tarefas;
    }

    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizarTarefa(Integer id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new NoContentException("Tarefa não encontrada"));

        return tarefaRepository.save(tarefa);
    }

    public void deletarTarefa(Integer id) {
        tarefaRepository.deleteById(id);
    }
}
