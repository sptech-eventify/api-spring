package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.dto.smartsync.TarefaDto;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.mapper.smartsync.TarefaMapper;
import eventify.api_spring.repository.BucketRepository;
import eventify.api_spring.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private BucketRepository bucketRepository;


    public List<TarefaDto> exibirTodasTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();

        if (tarefas.isEmpty()) {
            throw new NoContentException("Não há tarefas cadastradas");
        }

        List<TarefaDto> tarefasDto = tarefas.stream().map(TarefaMapper::toDto).collect(Collectors.toList());

        return tarefasDto;
    }

    public TarefaDto exibirTarefaPorId(Integer id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new NoContentException("Tarefa não encontrada"));
        TarefaDto tarefaDto = TarefaMapper.toDto(tarefa);

        return tarefaDto;
    }

    public List<TarefaDto> exibirTodasTarefasPorBucketId(Integer idBucket) {
        List<Tarefa> tarefas = tarefaRepository.findAllByBucketIdAndIsVisivelFalse(idBucket);

        if (tarefas.isEmpty()) {
            throw new NoContentException("Não há tarefas cadastradas");
        }

        List<TarefaDto> tarefasDto = tarefas.stream().map(TarefaMapper::toDto).collect(Collectors.toList());

        return tarefasDto;
    }

    public TarefaDto criarTarefa(TarefaDto tarefaCriacao) {
        Tarefa tarefa = new Tarefa();

        if (tarefaCriacao.getIdTarefaPai() != null) {
            if (tarefaRepository.findById(tarefaCriacao.getIdTarefaPai()).isEmpty()) {
                throw new NotFoundException("Tarefa pai não encontrada");
            } else {
                Tarefa tarefaPai = tarefaRepository.findById(tarefaCriacao.getIdTarefaPai()).get();
                tarefa.setTarefaPai(tarefaPai);
            }
        } else {
            tarefa.setTarefaPai(null);
        }

        tarefa.setNome(tarefaCriacao.getNome());
        tarefa.setDescricao(tarefaCriacao.getDescricao());
        tarefa.setFibonacci(tarefaCriacao.getFibonacci());
        tarefa.setStatus(tarefaCriacao.getStatus());
        tarefa.setHorasEstimada(tarefaCriacao.getHorasEstimada());
        tarefa.setDataEstimada(tarefaCriacao.getDataEstimada());
        tarefa.setDataCriacao(tarefaCriacao.getDataCriacao());
        tarefa.setDataConclusao(tarefaCriacao.getDataConclusao());
        tarefa.setIsVisivel(tarefaCriacao.getIsVisivel());

        if (tarefaCriacao.getIdBucket() != null) {
            tarefa.setBucket(bucketRepository.findById(tarefaCriacao.getIdBucket()).orElseThrow(() -> new NotFoundException("Bucket não encontrado")));
        } else {
            tarefa.setBucket(null);
        }

        tarefaRepository.save(tarefa);

        TarefaDto tarefaDto = TarefaMapper.toDto(tarefa);

        return tarefaDto;
    }

    public TarefaDto atualizarTarefa(Integer id, TarefaDto tarefaAtualizada) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new NoContentException("Tarefa não encontrada"));

        tarefa.setNome(tarefaAtualizada.getNome());
        tarefa.setDescricao(tarefaAtualizada.getDescricao());
        tarefa.setFibonacci(tarefaAtualizada.getFibonacci());
        tarefa.setStatus(tarefaAtualizada.getStatus());
        tarefa.setHorasEstimada(tarefaAtualizada.getHorasEstimada());
        tarefa.setDataEstimada(tarefaAtualizada.getDataEstimada());
        tarefa.setDataCriacao(tarefaAtualizada.getDataCriacao());
        tarefa.setDataConclusao(tarefaAtualizada.getDataConclusao());
        tarefa.setIsVisivel(tarefaAtualizada.getIsVisivel());

        if (tarefaAtualizada.getIdTarefaPai() != null) {
            Tarefa tarefaPai = tarefaRepository.findById(tarefaAtualizada.getIdTarefaPai()).orElseThrow(() -> new NotFoundException("Tarefa pai não encontrada"));
            tarefa.setTarefaPai(tarefaPai);
        } else {
            tarefa.setTarefaPai(null);
        }

        if (tarefaAtualizada.getIdBucket() != null) {
            tarefa.setBucket(bucketRepository.findById(tarefaAtualizada.getIdBucket()).orElseThrow(() -> new NotFoundException("Bucket não encontrado")));
        } else {
            tarefa.setBucket(null);
        }

        tarefaRepository.save(tarefa);

        TarefaDto tarefaDto = TarefaMapper.toDto(tarefa);

        return tarefaDto;
    }

    public void deletarTarefa(Integer id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new NoContentException("Tarefa não encontrada"));

        tarefa.setIsVisivel(false);

        tarefaRepository.save(tarefa);
    }
}
