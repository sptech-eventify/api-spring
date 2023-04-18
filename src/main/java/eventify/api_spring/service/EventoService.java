package eventify.api_spring.service;

import eventify.api_spring.domain.Evento;
import eventify.api_spring.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;


    public List<Evento> exibirTodosEventos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos;
    }

    public Optional<Evento> exibeEvento(String nome) {
        Optional<Evento> evento = eventoRepository.findByBuffet(nome);
        return evento;
    }

    public Evento criarEvento(Evento e) {
        Evento evento = eventoRepository.save(e);
        return evento;
    }
}
