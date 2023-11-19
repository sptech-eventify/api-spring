package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.repository.BucketRepository;
import eventify.api_spring.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private BucketRepository bucketRepository;


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

    public Tarefa criarTarefa(Integer id, Tarefa tarefa) {
        if (tarefaRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Tarefa não encontrada");
        }

        if (tarefa.getTarefaPai() != null) {
            Tarefa tarefaPai = tarefaRepository.findById(tarefa.getTarefaPai().getId()).orElseThrow(() -> new NotFoundException("Tarefa pai não encontrada"));
            tarefa.setTarefaPai(tarefaPai);
        }

        if (bucketRepository.findById(tarefa.getBucket().getId()).isEmpty()) {
            throw new NotFoundException("Bucket não encontrado");
        }

        tarefa.setNome(tarefa.getNome());
        tarefa.setDescricao(tarefa.getDescricao());
        tarefa.setFibonacci(tarefa.getFibonacci());
        tarefa.setStatus(tarefa.getStatus());
        tarefa.setHorasEstimada(tarefa.getHorasEstimada());
        tarefa.setDataEstimada(tarefa.getDataEstimada());
        tarefa.setDataCriacao(tarefa.getDataCriacao());
        tarefa.setDataConclusao(tarefa.getDataConclusao());
        tarefa.setIsVisivel(tarefa.getIsVisivel());

        tarefa.setBucket(tarefa.getBucket());

        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizarTarefa(Integer id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new NoContentException("Tarefa não encontrada"));

        return tarefaRepository.save(tarefa);
    }

    public void deletarTarefa(Integer id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new NoContentException("Tarefa não encontrada"));

        tarefa.setDataConclusao(tarefa.getDataConclusao());

        tarefaRepository.save(tarefa);
    }
}
