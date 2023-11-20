package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.dto.smartsync.SecaoDto;
import eventify.api_spring.dto.smartsync.TarefaDto;
import eventify.api_spring.dto.smartsync.TarefaSecaoDto;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.mapper.smartsync.TarefaMapper;
import eventify.api_spring.repository.BucketRepository;
import eventify.api_spring.repository.TarefaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private EntityManager entityManager;

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

    public List<TarefaSecaoDto> exibirTodasTarefasPorSecao(Integer idBuffet, Integer idEvento) {
        Query query = entityManager.createNativeQuery("SELECT * FROM vw_eventos_por_secao WHERE id_buffet = :idBuffet AND id_evento = :idEvento");
        query.setParameter("idBuffet", idBuffet);
        query.setParameter("idEvento", idEvento);
        List<Object[]> tarefas = query.getResultList();

        List<TarefaSecaoDto> tarefasDto = new ArrayList<>();
        for (Object[] tarefa : tarefas) {
            Integer idBuffetDto = (Integer) tarefa[0];
            Integer idBuffetServico = (Integer) tarefa[1];
            Integer idEventoDto = (Integer) tarefa[2];
            Integer id = (Integer) tarefa[3];
            String nome = (String) tarefa[4];
            String descricao = (String) tarefa[5];
            Integer fibonacci = (Integer) tarefa[6];
            Integer status = (Integer) tarefa[7];
            Integer horasEstimada = (Integer) tarefa[8];
            Date dataEstimada = (Date) tarefa[9];
            Timestamp dataCriacao = (Timestamp) tarefa[10];
            Timestamp dataConclusao = (Timestamp) tarefa[11];
            Byte isVisivel = (Byte) tarefa[12];
            Integer idTarefa = (Integer) tarefa[13];
            Integer idBucket = (Integer) tarefa[14];

            tarefasDto.add(new TarefaSecaoDto(idBuffetDto, idBuffetServico, idEventoDto, id, nome, descricao, fibonacci, status, horasEstimada, dataEstimada, dataCriacao, dataConclusao, isVisivel, idTarefa, idBucket));
        }

        if (tarefasDto.isEmpty()) {
            throw new NoContentException("Não há tarefas cadastradas");
        }

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

    public List<SecaoDto> exibirDadosDaSecao(Integer idBuffet, Integer idEvento) {
        Query query = entityManager.createNativeQuery("SELECT DISTINCT nome_servico, id_servico  FROM vw_secoes WHERE id_buffet = :idBuffet AND id_evento = :idEvento");
        query.setParameter("idBuffet", idBuffet);
        query.setParameter("idEvento", idEvento);
        List<Object[]> secoes = query.getResultList();

        List<SecaoDto> secoesDto = new ArrayList<>();
        for (Object[] secao : secoes) {
            Integer idServico = (Integer) secao[1];
            String nomeServico = (String) secao[0];
           
            secoesDto.add(new SecaoDto(idServico, nomeServico));
        }

        if (secoesDto.isEmpty()) {
            throw new NoContentException("Não há seções disponíveis");
        }

        return secoesDto;
    }
}
