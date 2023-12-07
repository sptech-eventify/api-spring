package eventify.api_spring.service.smartsync;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.dto.smartsync.dashboard.EventoProximoDto;
import eventify.api_spring.dto.smartsync.dashboard.KanbanStatusDto;
import eventify.api_spring.dto.smartsync.dashboard.RegistroDto;
import eventify.api_spring.dto.smartsync.dashboard.RegistroKpiDto;
import eventify.api_spring.exception.http.NoContentException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class DashboardService {
    @Autowired
    private EntityManager entityManager;

    public List<EventoProximoDto> retornarListagemProximosEventos(Integer idBuffet) {
        String sql = "SELECT * FROM vw_listagem_proximos_eventos WHERE id_buffet = :idBuffet";
        List<Object[]> eventos = entityManager.createNativeQuery(sql).setParameter("idBuffet", idBuffet).getResultList();

        List<EventoProximoDto> eventosDto = new ArrayList();
        for (Object[] evento : eventos) {
            EventoProximoDto eventoDto = new EventoProximoDto();

            eventoDto.setCliente((String) evento[1]);
            eventoDto.setId((Integer) evento[2]);
            eventoDto.setData((java.sql.Timestamp) evento[3]);
            eventoDto.setStatus((Integer) evento[4]);

            Long tarefasRealizadas = (Long) evento[5];
            eventoDto.setTarefasRealizadas(tarefasRealizadas.intValue());
        
            Long tarefasPendentes = (Long) evento[6];
            eventoDto.setTarefasPendentes(tarefasPendentes.intValue());

            Long tarefasEmAndamento = (Long) evento[7];
            eventoDto.setTarefasEmAndamento(tarefasEmAndamento.intValue());

            eventosDto.add(eventoDto);
        }

        return eventosDto;
    }

    public List<KanbanStatusDto> retornarListagemDadosProximosEventos(Integer idBuffet) {
        Query queryTarefas = entityManager.createNativeQuery("SELECT * FROM vw_status_eventos WHERE id_buffet = :idBuffet");
        queryTarefas.setParameter("idBuffet", idBuffet);
        List<Object[]> eventos = queryTarefas.getResultList();

        List<KanbanStatusDto> kanbans = new ArrayList<>();
        for (Object[] evento : eventos) {
            Integer idEvento = (Integer) evento[1];
            String nomeCliente = (String) evento[2];
            LocalDate dataEvento = (Timestamp) evento[3] != null ? ((Timestamp) evento[3]).toLocalDateTime().toLocalDate() : null;
            LocalDate dataEstimada = (Date) evento[4] != null ? ((Date) evento[4]).toLocalDate() : null;
            Integer tarefasRealizadas = ((Long) evento[5]).intValue();
            Integer tarefasPendentes = ((Long) evento[6]).intValue();
            Integer tarefasEmAndamento = ((Long) evento[7]).intValue();
            Integer idServico = (Integer) evento[8];

            kanbans.add(new KanbanStatusDto(idEvento, nomeCliente, dataEvento, dataEstimada, tarefasEmAndamento, tarefasPendentes, tarefasRealizadas, idServico));
        }

        if (kanbans.isEmpty()) {
            throw new NoContentException("Não há dados disponíveis");
        }

        return kanbans;
    }

    public List<RegistroDto> retornarRetencaoUsuarios() {
        String sql = "SELECT * FROM vw_grafico";
        List<Object[]> registros = entityManager.createNativeQuery(sql).getResultList();

        List<RegistroDto> registrosDto = new ArrayList();
        for (Object[] registro : registros) {
            RegistroDto registroDto = new RegistroDto();

            registroDto.setAno((Integer) registro[0]);
            registroDto.setMes((String) registro[2]);
            registroDto.setEntraram((Long) registro[3]);
            registroDto.setSairam((Long) registro[4]);

            registrosDto.add(registroDto);
        }

        return registrosDto;
    }

    public RegistroKpiDto retornarRetencaoUsuariosKpis() {
        RegistroKpiDto registroDto = new RegistroKpiDto();

        String sql = "SELECT * FROM vw_ultimas_7_dias";
        Object ultimos7dias = entityManager.createNativeQuery(sql).getSingleResult();

        sql = "SELECT * FROM vw_ultimas_90_dias";
        Object ultimos90dias = entityManager.createNativeQuery(sql).getSingleResult();

        sql = "SELECT * FROM vw_retencao_usuario";
        Object retencao = entityManager.createNativeQuery(sql).getSingleResult();

        sql = "SELECT * FROM vw_curiosidades_usuario";
        List<Object[]> curiosidades = entityManager.createNativeQuery(sql).getResultList();

        registroDto.setUltimaSemana((Long) ultimos7dias);
        registroDto.setUltimosTresMeses((Long) ultimos90dias);
        registroDto.setTotal((Long) retencao);

        for (Object[] curiosidade : curiosidades) {
            Integer idTipoUsuario = (Integer) curiosidade[0];
            BigDecimal porcentagem = (BigDecimal) curiosidade[2];

            if (idTipoUsuario == 1) {
                registroDto.setPercentualContratantes(porcentagem);
            } else if (idTipoUsuario == 2) {
                registroDto.setPercentualProprietarios(porcentagem);
            }
        }



        return registroDto;
    }
}