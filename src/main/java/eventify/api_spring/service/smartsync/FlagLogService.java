package eventify.api_spring.service.smartsync;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.domain.smartsync.FlagLog;
import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.dto.smartsync.FlagLogCriacaoDto;
import eventify.api_spring.dto.smartsync.FlagLogDto;
import eventify.api_spring.exception.http.BadRequestException;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.mapper.FlagLogRepository;
import eventify.api_spring.mapper.smartsync.FlagLogMapper;
import eventify.api_spring.mapper.smartsync.TarefaMapper;
import eventify.api_spring.repository.FuncionarioRepository;
import eventify.api_spring.repository.TarefaRepository;
import eventify.api_spring.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class FlagLogService {
    @Autowired
    private FlagLogRepository flagLogRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private EntityManager entityManager;

    public List<FlagLogDto> retornarFlagLog(Integer idExecutor, Boolean isFuncionario) {
        List<FlagLog> flagLog = new ArrayList<FlagLog>();

        if (isFuncionario) {
            flagLog = flagLogRepository.findAllByFuncionario(funcionarioRepository.findById(idExecutor).orElseThrow(() -> new NotFoundException("Funcionario não encontrado")));
        } else {
            flagLog = flagLogRepository.findAllByUsuario(usuarioRepository.findById(idExecutor).orElseThrow(() -> new NotFoundException("Usuário não encontrado")));
        }

        if (flagLog.isEmpty()) {
            throw new NoContentException("FlagLog não encontrado");
        }

        return flagLog.stream().map(FlagLogMapper::toDto).collect(Collectors.toList());
    }

    public FlagLogDto retornarFlagLogIndividual(Integer id) {
        FlagLog flagLog = flagLogRepository.findById(id).orElseThrow(() -> new NotFoundException("FlagLog não encontrado"));

        return FlagLogMapper.toDto(flagLog);
    }

    public List<FlagLogDto> retornarFlagLogPorSecao(Integer idExecutor, Boolean isFuncionario, Integer idSecao) {
        List<FlagLogDto> flagLog = new ArrayList<FlagLogDto>();
        String sql = "";

        if (isFuncionario) {
            sql = "SELECT * FROM vw_flag_log_tarefa WHERE id_funcionario = :idExecutor AND id_secao = :idSecao";
        } else {
            sql = "SELECT * FROM vw_flag_log_tarefa WHERE id_usuario = :idExecutor AND id_secao = :idSecao";
        }

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("idExecutor", idExecutor);
        query.setParameter("idSecao", idSecao);
        List<Object[]> logs = query.getResultList();

        if (logs.isEmpty()) {
            throw new NoContentException("FlagLog não encontrado");
        }

        for (Object[] log : logs) {
            FlagLogDto flag = new FlagLogDto();
            flag.setId((Integer) log[1]);
            flag.setDataCriacao((java.sql.Timestamp) log[3]);

            Tarefa tarefa = tarefaRepository.findById((Integer) log[2]).orElseThrow(() -> new NotFoundException("Tarefa não encontrada"));
            flag.setTarefa(TarefaMapper.toDto(tarefa));

            flagLog.add(flag);
        }

        return flagLog;
    }

    public FlagLog criarFlagLog(FlagLogCriacaoDto flagLogCriacaoDto) {
        FlagLog flagLog = new FlagLog();

        if (flagLogCriacaoDto.getIdFuncionario() != null) {
            flagLog.setFuncionario(funcionarioRepository.findById(flagLogCriacaoDto.getIdFuncionario()).orElseThrow(() -> new NotFoundException("Funcionário não encontrado")));
        } else if (flagLogCriacaoDto.getIdUsuario() != null) {
            flagLog.setUsuario(usuarioRepository.findById(flagLogCriacaoDto.getIdUsuario()).orElseThrow(() -> new NotFoundException("Usuário não encontrado")));
        } else {
            throw new BadRequestException("Funcionário ou usuário não encontrado");
        }

        flagLog.setTarefa(tarefaRepository.findById(flagLogCriacaoDto.getIdTarefa()).orElseThrow(() -> new NotFoundException("Tarefa não encontrada")));
        flagLog.setDataCriacao(flagLogCriacaoDto.getDataCriacao());

        return flagLogRepository.save(flagLog);
    }

    public void deletarFlagLog(Integer id) {
        FlagLog flagLog = flagLogRepository.findById(id).orElseThrow(() -> new NotFoundException("FlagLog não encontrado"));

        flagLog.setIsVisivel(false);
        flagLogRepository.save(flagLog);
    }
}