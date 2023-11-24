package eventify.api_spring.service.smartsync;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.dto.smartsync.dashboard.EventoProximoDto;
import jakarta.persistence.EntityManager;

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
}