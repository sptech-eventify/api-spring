package eventify.api_spring.service;

import eventify.api_spring.domain.Agenda;
import eventify.api_spring.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public List<Agenda> exibirAgendas() {
        return agendaRepository.findAll();
    }

    public void criarAgenda(Agenda a) {
        agendaRepository.save(a);
    }


}
