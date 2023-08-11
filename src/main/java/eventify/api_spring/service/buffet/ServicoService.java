package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.Servico;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {
    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> servicos() {
        List<Servico> servicos = servicoRepository.findAll();

        if (servicos.isEmpty()) {
            throw new NoContentException("Não há serviços cadastrados");
        } 

        return servicos;
    }
}
