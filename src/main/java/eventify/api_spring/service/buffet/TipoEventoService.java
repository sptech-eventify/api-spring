package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.TipoEvento;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.repository.TipoEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEventoService {
    @Autowired
    private TipoEventoRepository tipoEventoRepository;

    public List<TipoEvento> tiposEventos() {
        List<TipoEvento> tiposEventos = tipoEventoRepository.findAll();

        if (tiposEventos.isEmpty()) {
            throw new NoContentException("Não há tipos de eventos cadastrados");
        }

        return tiposEventos;
    }
}
