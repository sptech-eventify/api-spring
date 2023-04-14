package eventify.sptech.apispring.service;

import eventify.sptech.apispring.entity.Buffet;
import eventify.sptech.apispring.entity.Evento;
import eventify.sptech.apispring.entity.TipoEvento;
import eventify.sptech.apispring.repository.BuffetRepository;
import eventify.sptech.apispring.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuffetService {

    @Autowired
    private BuffetRepository buffetRepository;
    @Autowired
    private EventoRepository eventoRepository;

    public List<String> getTipoEventos() {
        List<Buffet> buffets = buffetRepository.findAll();
        List<String> tipos  = new ArrayList<>();
        for (Buffet buffet : buffets) {
            for (TipoEvento evento  : buffet.getTiposEventos()) {
                if (!tipos.contains(evento.getDescricao())) {
                    tipos.add(evento.getDescricao());
                }
            }
        }
        return tipos;
    }

    public Double getAvaliacaoEvento(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isPresent()) {
            Buffet buffet = buffetOpt.get();
            return eventoRepository.findByBuffet(buffet).stream()
                    .mapToDouble(Evento::getNota)
                    .average()
                    .orElse(0.0);
        }
        return null;
    }

}
