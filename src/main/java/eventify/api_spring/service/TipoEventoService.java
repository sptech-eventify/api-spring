package eventify.api_spring.service;

import eventify.api_spring.domain.TipoEvento;
import eventify.api_spring.repository.TipoEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEventoService {


    @Autowired
    private TipoEventoRepository tipoEventoRepository;

    public TipoEvento criarTipoEvento(TipoEvento t) {
        return this.tipoEventoRepository.save(t);
    }

    public List<TipoEvento> exibirTipoEvento() {
        return this.tipoEventoRepository.findAll();
    }
}
