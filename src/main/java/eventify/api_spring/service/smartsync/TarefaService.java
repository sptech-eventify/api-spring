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

import java.time.LocalDateTime;
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
        List<Tarefa> tarefas = tarefaRepository.findAllByBucketId(idBucket);

        if (tarefas.isEmpty()) {
            throw new NoContentException("Não há tarefas cadastradas");
        }

        List<TarefaDto> tarefasDto = tarefas.stream().map(TarefaMapper::toDto).collect(Collectors.toList());

        return tarefasDto;
    }

    public TarefaDto criarTarefa(Integer id, Tarefa tarefa) {
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

        tarefa.setDataConclusao(LocalDateTime.now());

        tarefaRepository.save(tarefa);
    }
}
